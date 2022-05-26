package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.UseCaseHandleBaseTest;
import co.com.sofka.cargame.domain.juego.command.CrearJuegoCommand;
import co.com.sofka.cargame.domain.juego.events.JuegoCreado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CrearJuegoUseCaseTest extends UseCaseHandleBaseTest {

    @Test
    void crearJuego_casoFeliz() throws InterruptedException {
        var useCase = new CrearJuegoUseCase();
        var juegoId = "xxxx";
        var command = new CrearJuegoCommand(5, juegoId, Map.of(
                "111", "Raul Alzate",
                "222", "Andres",
                "333", "Ana"
        ));
        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new RequestCommand<>(command))
                .subscribe(subscriber);
        Thread.sleep(1500);
        verify(subscriber, times(4)).onNext(eventCaptor.capture());

        var juegoCreadp = (JuegoCreado) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals(5, juegoCreadp.getPista().value().kilometros());
        Assertions.assertEquals(3, juegoCreadp.getPista().value().numeroDeCarriles());

    }
}