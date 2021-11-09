package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.domain.juego.command.InicarJuegoCommand;
import co.com.sofka.cargame.domain.juego.events.JuegoCreado;
import co.com.sofka.cargame.domain.juego.events.JuegoIniciado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.Pista;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InicarJuegoUseCaseTest extends UseCaseHandleBaseTest {

    @Test
    void iniciarJuego_casoFeliz() throws InterruptedException {
        var useCase = new InicarJuegoUseCase();
        var command = new InicarJuegoCommand("xxxx");

        when(repository.getEventsBy("xxxx")).thenReturn(List.of(
                new JuegoCreado(new Pista(5, 3))
        ));
        useCase.addRepository(repository);

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxx")
                .asyncExecutor(useCase, new RequestCommand<>(command))
                .subscribe(subscriber);
        Thread.sleep(800);
        verify(subscriber).onNext(eventCaptor.capture());
        var juegoIniciado = (JuegoIniciado) eventCaptor.getValue();
        Assertions.assertEquals("xxxx", juegoIniciado.aggregateRootId());
    }

    @Test
    void elJuegoFinalizo() throws InterruptedException {
        var useCase = new InicarJuegoUseCase();
        var command = new InicarJuegoCommand("xxxx");

        when(repository.getEventsBy("xxxx")).thenReturn(List.of(
                new JuegoCreado(new Pista(5, 3)),
                new JuegoIniciado()
        ));
        useCase.addRepository(repository);

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxx")
                .asyncExecutor(useCase, new RequestCommand<>(command))
                .subscribe(subscriber);

        verify(subscriber).onError(eventCaptorError.capture());
        Thread.sleep(600);
        var error = eventCaptorError.getValue();
        Assertions.assertEquals("Ya termino el Juego", error.getMessage());
    }
}