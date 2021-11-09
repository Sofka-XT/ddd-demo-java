package co.com.sofka.cargame.infra.services;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.command.MoverCarroCommand;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.usecase.MoverCarroUseCase;
import co.com.sofka.cargame.usecase.services.MoverCarroService;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoverCarroCommandService implements MoverCarroService {
    private final EventStoreRepository eventStoreRepository;
    private final MoverCarroUseCase moverCarroUseCase;
    private final SubscriberEvent subscriberEvent;

    @Autowired
    public MoverCarroCommandService(
            EventStoreRepository eventStoreRepository,
            SubscriberEvent subscriberEvent,
            MoverCarroUseCase moverCarroUseCase) {
        this.eventStoreRepository = eventStoreRepository;
        this.moverCarroUseCase = moverCarroUseCase;
        this.subscriberEvent = subscriberEvent;
    }

    @Override
    public void mover(CarroId carroId, CarrilId carrilId) {
        var command = new MoverCarroCommand(carroId, carrilId);
        moverCarroUseCase.addRepository(domainEventRepository());
        UseCaseHandler.getInstance()
                .setIdentifyExecutor(carroId.value())
                .asyncExecutor(moverCarroUseCase, new RequestCommand<>(command))
                .subscribe(subscriberEvent);
    }

    private DomainEventRepository domainEventRepository() {
        return new DomainEventRepository() {
            @Override
            public List<DomainEvent> getEventsBy(String aggregateId) {
                return eventStoreRepository.getEventsBy("carro", aggregateId);
            }

            @Override
            public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
                return eventStoreRepository.getEventsBy(aggregateName, aggregateRootId);
            }
        };
    }

}
