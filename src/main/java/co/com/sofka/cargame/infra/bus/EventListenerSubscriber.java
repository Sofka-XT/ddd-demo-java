package co.com.sofka.cargame.infra.bus;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EventListenerSubscriber implements Flow.Subscriber<DomainEvent> {
    private static final Logger logger = Logger.getLogger(EventListenerSubscriber.class.getName());
    private final Set<UseCase.UseCaseWrap> useCases;
    private final EventStoreRepository repository;
    private final SubscriberEvent subscriberEvent;
    private final ServiceBuilder serviceBuilder;


    @Autowired
    public EventListenerSubscriber(
            Set<UseCase.UseCaseWrap> useCases,
            EventStoreRepository repository,
            ServiceBuilder serviceBuilder,
            SubscriberEvent subscriberEvent) {
        this.useCases = useCases;
        this.repository = repository;
        this.serviceBuilder = serviceBuilder;
        this.subscriberEvent = subscriberEvent;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
    }

    @Override
    public void onNext(DomainEvent domainEvent) {
        DomainEvent event = Objects.requireNonNull(domainEvent);
        this.useCases.stream().filter((useCaseWrap) -> useCaseWrap.eventType().equals(domainEvent.type)).forEach((useCaseWrap) -> {
            UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> useCase = useCaseWrap.usecase();
            useCase.addServiceBuilder(serviceBuilder);
            useCase.addRepository(new DomainEventRepository() {
                public List<DomainEvent> getEventsBy(String aggregateRootId) {
                    return EventListenerSubscriber.this.repository.getEventsBy(event.getAggregateName(), aggregateRootId);
                }

                public List<DomainEvent> getEventsBy(String aggregate, String aggregateRootId) {
                    return EventListenerSubscriber.this.repository.getEventsBy(aggregate, aggregateRootId);
                }
            });
            UseCaseHandler.getInstance()
                    .setIdentifyExecutor(event.aggregateRootId())
                    .asyncExecutor(useCase, new TriggeredEvent<>(event))
                    .subscribe(this.subscriberEvent);
        });
    }

    @Override
    public void onError(Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    @Override
    public void onComplete() {

    }

}