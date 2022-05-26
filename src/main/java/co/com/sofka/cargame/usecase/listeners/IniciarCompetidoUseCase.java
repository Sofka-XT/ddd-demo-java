package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.events.CompetidorIniciado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.services.MoverCarroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class IniciarCompetidoUseCase extends UseCase<TriggeredEvent<CompetidorIniciado>, ResponseEvents> {
    private static final Logger logger = Logger.getLogger(IniciarCompetidoUseCase.class.getName());

    @Override
    public void executeUseCase(TriggeredEvent<CompetidorIniciado> competidorIniciadoTriggeredEvent) {
        var event = competidorIniciadoTriggeredEvent.getDomainEvent();
        var moverCarroService = getService(MoverCarroService.class).orElseThrow();
        var events = repository().getEventsBy("juego", event.aggregateRootId());
        var jugando = Juego.from(JuegoId.of(event.aggregateRootId()), events).jugando();
        //loop
        while (jugando){
            moverCarroService.mover(event.getCarroId(), event.getCarrilId());
            logger.info("Running => "+event.getCarrilId());
            events = repository().getEventsBy("juego", event.aggregateRootId());
            jugando = Juego.from(
                    JuegoId.of(event.aggregateRootId()), events
            ).jugando();
            esperar4Segundos();
        }

        emit().onResponse(new ResponseEvents(List.of()));
    }

    private void esperar4Segundos() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
