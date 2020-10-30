package co.com.sofka.cargame.usecase;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.carril.Carril;

import java.util.logging.Level;
import java.util.logging.Logger;

@EventListener(eventType = "cargame.car.KilometrajeCambiado")
public class MoverCarroEnCarrilUseCase extends UseCase<TriggeredEvent<KilometrajeCambiado>, ResponseEvents> {
    private static final Logger logger = Logger.getLogger(MoverCarroEnCarrilUseCase.class.getName());
    @Override
    public void executeUseCase(TriggeredEvent<KilometrajeCambiado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var nuevaPosicion = event.getDistancia();
        var carril = Carril.from(event.getCarrilId(), retrieveEvents());

        if(carril.desplazamientoFinal().equals(Boolean.FALSE)){
            carril.moverCarro(nuevaPosicion);
            if(carril.posicionActual() >= carril.posicionDeseada()){
                logger.log(Level.INFO, "El carro finalizo con una distancia de {0} metros", carril.posicionActual());
                carril.alcazarLaMeta();
            }
            emit().onSuccess(new ResponseEvents(carril.getUncommittedChanges()));
        }

    }
}
