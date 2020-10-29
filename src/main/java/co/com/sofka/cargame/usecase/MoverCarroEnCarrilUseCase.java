package co.com.sofka.cargame.usecase;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.carril.Carril;

@EventListener(eventType = "cargame.car.KilometrajeCambiado")
public class MoverCarroEnCarrilUseCase extends UseCase<TriggeredEvent<KilometrajeCambiado>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<KilometrajeCambiado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        //var carroId = CarroId.of(event.aggregateRootId());
        //var service = getService(CarroServiceQuery.class).orElseThrow();
        //var carrilId = service.encontrarCarrilIdPor(carroId);

        var carril = Carril.from(event.getCarrilId(), retrieveEvents());
        carril.moverCarro(event.getDistancia());
        emit().onSuccess(new ResponseEvents(carril.getUncommittedChanges()));

    }
}
