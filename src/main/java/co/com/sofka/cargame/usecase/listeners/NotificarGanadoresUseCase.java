package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.juego.events.JuegoFinalizado;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class NotificarGanadoresUseCase extends UseCase<TriggeredEvent<JuegoFinalizado>, ResponseEvents> {
    private static final Logger logger = Logger.getLogger(NotificarGanadoresUseCase.class.getName());

    @Override
    public void executeUseCase(TriggeredEvent<JuegoFinalizado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var podioPros = event.getPodio().value();
        logger.info("--------------- PrimerLugar:" + podioPros.primerLugar().nombre().value());
        logger.info("------------ SegundoLugar:" + podioPros.segundoLugar().nombre().value());
        logger.info(" ----- Tercer Lugar:" + podioPros.tercerLugar().nombre().value());
        emit().onResponse(new ResponseEvents(List.of()));
    }
}
