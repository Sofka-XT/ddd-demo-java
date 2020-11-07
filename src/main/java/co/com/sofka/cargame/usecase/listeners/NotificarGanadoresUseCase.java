package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.juego.events.JuegoFinalizado;

import java.util.List;

@EventListener(eventType = "juego.JuegoFinalizado")
public class NotificarGanadoresUseCase extends UseCase<TriggeredEvent<JuegoFinalizado>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<JuegoFinalizado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var podioPros = event.getPodio().value();
        System.out.println("primerLugar:" + podioPros.primerLugar());
        System.out.println("segundoLugar:"+podioPros.segundoLugar());
        System.out.println("tercerLugar:"+podioPros.tercerLugar());
        emit().onSuccess(new ResponseEvents(List.of()));
    }
}
