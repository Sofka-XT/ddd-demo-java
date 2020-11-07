package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.Carril;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.usecase.services.JuegoService;

@EventListener(eventType = "carro.CarroCreado")
public class CrearCarrilUseCase  extends UseCase<TriggeredEvent<CarroCreado>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<CarroCreado> carroCreadoTriggeredEvent) {
        var event = carroCreadoTriggeredEvent.getDomainEvent();
        var service = getService(JuegoService.class).orElseThrow();
        var kilometros = service.getKilometros(event.getJuegoId());

        var carril = new Carril(new CarrilId(), event.getJuegoId(), kilometros*1000);
        carril.aggregarCarro(event.getPlaca());
        emit().onSuccess(new ResponseEvents(carril.getUncommittedChanges()));
    }


}
