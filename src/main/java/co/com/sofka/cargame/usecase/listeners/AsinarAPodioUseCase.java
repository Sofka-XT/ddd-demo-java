package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.usecase.services.CarroService;

import java.util.Objects;

@EventListener(eventType = "carril.CarroFinalizoSuRecorrido")
public class AsinarAPodioUseCase extends UseCase<TriggeredEvent<CarroFinalizoSuRecorrido>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<CarroFinalizoSuRecorrido> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var juego = Juego.from(event.getJuegoId(), retrieveEvents());
        var service = getService(CarroService.class).orElseThrow();
        var conductorId = service.getConductorIdPor(event.getCarroId());

        if(Objects.isNull(juego.podio().primerLugar())){
            juego.asignarPrimerLugar(JugadorId.of(conductorId));
        } else if(Objects.isNull(juego.podio().segundoLugar())){
            juego.asignarSegundoLugar(JugadorId.of(conductorId));
        } else if(Objects.isNull(juego.podio().tercerLugar())){
            juego.asignarTercerLugar(JugadorId.of(conductorId));
        }
        emit().onSuccess(new ResponseEvents(juego.getUncommittedChanges()));
    }

}
