package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.Carril;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MoverCarroEnCarrilUseCase extends UseCase<TriggeredEvent<KilometrajeCambiado>, ResponseEvents> {
    private static final Logger logger = Logger.getLogger(MoverCarroEnCarrilUseCase.class.getName());

    @Override
    public void executeUseCase(TriggeredEvent<KilometrajeCambiado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var nuevaPosicion = event.getDistancia();
        var events = repository().getEventsBy("carril", event.getCarrilId().value());
        var carril = Carril.from(event.getCarrilId(), events);
        logger.log(Level.INFO, "####### CARRO {0} ", carril.carroId());
        if (carril.desplazamientoFinal().equals(Boolean.FALSE)) {
            carril.moverCarro(nuevaPosicion);
            if (carril.posicionActual() >= carril.posicionDeseada()) {
                carril.alcazarLaMeta();
            }
            emit().onResponse(new ResponseEvents(carril.getUncommittedChanges()));
        }
    }
}
