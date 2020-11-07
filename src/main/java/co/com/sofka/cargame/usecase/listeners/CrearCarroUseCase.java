package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carro.Carro;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.cargame.domain.juego.events.JugadorCreado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;

@EventListener(eventType = "juego.JugadorCreado")
public class CrearCarroUseCase extends UseCase<TriggeredEvent<JugadorCreado>, ResponseEvents> {

    @Override
    public void executeUseCase(TriggeredEvent<JugadorCreado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var carro = new Carro(
                new CarroId(),
                JuegoId.of(event.aggregateRootId()),
                event.getColor()
        );
        carro.asignarConductor(
                event.getNombre().value(),
                Cedula.of(event.getJugadorId().value())
        );
        emit().onSuccess(new ResponseEvents(carro.getUncommittedChanges()));
    }
}
