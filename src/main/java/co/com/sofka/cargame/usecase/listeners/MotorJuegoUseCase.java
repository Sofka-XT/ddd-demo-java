package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.command.MoverCarroCommand;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.events.JuegoIniciado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.MoverCarroUseCase;
import co.com.sofka.cargame.usecase.model.CarroSobreCarril;
import co.com.sofka.cargame.usecase.services.CarrilCarroService;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

@EventListener(eventType = "juego.JuegoIniciado")
public class MotorJuegoUseCase  extends UseCase<TriggeredEvent<JuegoIniciado>, ResponseEvents> {

    private static final Logger logger = Logger.getLogger(MotorJuegoUseCase.class.getName());
    private final MoverCarroUseCase moverCarroUseCase;
    private final Flow.Subscriber<? super DomainEvent> subscriber;

    public MotorJuegoUseCase(MoverCarroUseCase moverCarroUseCase, Flow.Subscriber<? super DomainEvent> subscriber){
        this.moverCarroUseCase = moverCarroUseCase;
        this.subscriber = subscriber;
    }

    @Override
    public void executeUseCase(TriggeredEvent<JuegoIniciado> triggeredEvent) {
        var event = triggeredEvent.getDomainEvent();
        var juegoId = JuegoId.of(event.aggregateRootId());
        var service = getService(CarrilCarroService.class).orElseThrow();
        var competidores = service.getCarrosSobreCarriles(juegoId);
        logger.log(Level.INFO, "stating game with {0}", competidores.toString());
        boolean jugando;
        if(!competidores.isEmpty()) {
            do{

                competidores.forEach(carroSobreCarril -> {
                    var moverCarro = new MoverCarroCommand(
                            CarroId.of(carroSobreCarril.getCarroId()),
                            CarrilId.of(carroSobreCarril.getCarrilId())
                    );

                    UseCaseHandler.getInstance()
                            .setIdentifyExecutor(moverCarro.getCarroId().value())
                            .asyncExecutor(moverCarroUseCase, new RequestCommand<>(moverCarro))
                            .subscribe(subscriber);
                    esperar2Segundos();
                });
                jugando = Juego.from(juegoId, retrieveEvents()).jugando();
            } while (jugando);
        }

        emit().onSuccess(new ResponseEvents(List.of()));
    }

    private void esperar2Segundos() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
