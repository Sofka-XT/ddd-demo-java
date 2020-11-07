package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.Carril;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;

import java.util.logging.Level;
import java.util.logging.Logger;

@EventListener(eventType = "carro.KilometrajeCambiado")
public class MoverCarroEnCarrilUseCase extends UseCase<TriggeredEvent<KilometrajeCambiado>, ResponseEvents> {
    private static final Logger logger = Logger.getLogger(MoverCarroEnCarrilUseCase.class.getName());

    @Override
    public void executeUseCase(TriggeredEvent<KilometrajeCambiado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var nuevaPosicion = event.getDistancia();
        var events = retrieveEvents();
        var carril = Carril.from(event.getCarrilId(),events);
        System.out.println(events);
        logger.log(Level.INFO, "####### El en movimiento adicionando {0} mestros", carril.posicionActual());

        if (carril.desplazamientoFinal().equals(Boolean.FALSE)) {
            carril.moverCarro(nuevaPosicion);
            if (carril.posicionActual() >= carril.posicionDeseada()) {
                logger.log(Level.INFO, "####### El carro finalizo con una distancia de {0} metros", carril.posicionActual());
                carril.alcazarLaMeta();
            }
            emit().onSuccess(new ResponseEvents(carril.getUncommittedChanges()));
        }

    }
}
