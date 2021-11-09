package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.events.JuegoIniciado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.services.CarrilCarroService;
import co.com.sofka.cargame.usecase.services.MoverCarroService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MotorJuegoUseCase extends UseCase<TriggeredEvent<JuegoIniciado>, ResponseEvents> {

    @Override
    public void executeUseCase(TriggeredEvent<JuegoIniciado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var juegoId = JuegoId.of(event.aggregateRootId());
        var carrilCarroService = getService(CarrilCarroService.class).orElseThrow();
        var moverCarroService = getService(MoverCarroService.class).orElseThrow();
        var competidores = carrilCarroService.getCarrosSobreCarriles(juegoId);
        boolean jugando;
        if (!competidores.isEmpty()) {
            do {

                competidores.forEach(carroSobreCarril -> {
                    moverCarroService.mover(
                            CarroId.of(carroSobreCarril.getCarroId()),
                            CarrilId.of(carroSobreCarril.getCarrilId())
                    );
                    esperar4Segundos();
                });

                jugando = Juego.from(juegoId, retrieveEvents()).jugando();
            } while (jugando);
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
