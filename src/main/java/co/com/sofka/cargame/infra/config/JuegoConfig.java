package co.com.sofka.cargame.infra.config;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.infra.bus.EventListenerSubscriber;
import co.com.sofka.cargame.infra.bus.EventSubscriber;
import co.com.sofka.cargame.infra.bus.NATSEventSubscriber;
import co.com.sofka.cargame.infra.services.CarrilCarroQueryService;
import co.com.sofka.cargame.infra.services.CarroQueryService;
import co.com.sofka.cargame.infra.services.JuegoQueryService;
import co.com.sofka.cargame.usecase.MoverCarroUseCase;
import co.com.sofka.cargame.usecase.listeners.*;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Configuration
public class JuegoConfig {

    @Bean
    public SubscriberEvent subscriberEvent(EventStoreRepository eventStoreRepository, EventBus eventBus) {
        return new SubscriberEvent(eventStoreRepository, eventBus);
    }

    @Bean
    public EventSubscriber eventSubscriber(@Value("${spring.nats.uri}") String uri, EventListenerSubscriber eventListenerSubscriber) throws IOException, InterruptedException {
        var eventSubs = new NATSEventSubscriber(uri, eventListenerSubscriber);
        eventSubs.subscribe("juego.>", "handles.juego");
        eventSubs.subscribe("carro.>", "handles.carro");
        eventSubs.subscribe("carril.>", "handles.carril");
        return eventSubs;
    }

    @Bean
    public ServiceBuilder serviceBuilder(
            CarrilCarroQueryService carrilCarroService, CarroQueryService carroQueryService, JuegoQueryService juegoQueryService
    ) {
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        serviceBuilder.addService(carrilCarroService);
        serviceBuilder.addService(carroQueryService);
        serviceBuilder.addService(juegoQueryService);
        return serviceBuilder;
    }

    @Bean
    public Set<UseCase.UseCaseWrap> useCases(SubscriberEvent subscriberEvent, EventStoreRepository eventStoreRepository, ServiceBuilder serviceBuilder) {


        var moverCarro = new MoverCarroUseCase();

        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> asinarAPodioUseCase = (UseCase) new AsinarAPodioUseCase();
        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> crearCarrilUseCase = (UseCase) new CrearCarrilUseCase();
        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> crearCarroUseCase = (UseCase) new CrearCarroUseCase();
        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> moverCarroEnCarrilUseCase = (UseCase) new MoverCarroEnCarrilUseCase();
        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> motorJuegoUseCase = (UseCase) new MotorJuegoUseCase(moverCarro, subscriberEvent);
        UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> notificarGanador = (UseCase) new NotificarGanadoresUseCase();

        moverCarro.addServiceBuilder(serviceBuilder);
        notificarGanador.addServiceBuilder(serviceBuilder);
        asinarAPodioUseCase.addServiceBuilder(serviceBuilder);
        crearCarrilUseCase.addServiceBuilder(serviceBuilder);
        crearCarroUseCase.addServiceBuilder(serviceBuilder);
        moverCarroEnCarrilUseCase.addServiceBuilder(serviceBuilder);
        motorJuegoUseCase.addServiceBuilder(serviceBuilder);

        moverCarro.addRepository(domainEventRepository(eventStoreRepository));


        return Set.of(
                new UseCase.UseCaseWrap("carril.CarroFinalizoSuRecorrido", asinarAPodioUseCase),
                new UseCase.UseCaseWrap("carro.CarroCreado", crearCarrilUseCase),
                new UseCase.UseCaseWrap("juego.JugadorCreado", crearCarroUseCase),
                new UseCase.UseCaseWrap("juego.JuegoIniciado", motorJuegoUseCase),
                new UseCase.UseCaseWrap("carro.KilometrajeCambiado", moverCarroEnCarrilUseCase),
                new UseCase.UseCaseWrap("juego.JuegoFinalizado", notificarGanador)
        );
    }

    private DomainEventRepository domainEventRepository(EventStoreRepository eventStoreRepository) {
        return new DomainEventRepository() {
            @Override
            public List<DomainEvent> getEventsBy(String aggregateId) {
                return eventStoreRepository.getEventsBy("carro", aggregateId);
            }

            @Override
            public List<DomainEvent> getEventsBy(String aggregateName, String aggregateId) {
                return eventStoreRepository.getEventsBy(aggregateName, aggregateId);
            }
        };
    }
}
