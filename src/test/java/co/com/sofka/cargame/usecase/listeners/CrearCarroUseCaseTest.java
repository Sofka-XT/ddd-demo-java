package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.juego.events.JugadorCreado;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.cargame.usecase.UseCaseHandleBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CrearCarroUseCaseTest extends UseCaseHandleBaseTest {
    @Test
    void crearCarro_casoFeliz() {
        var useCase = new CrearCarroUseCase();
        var event = new JugadorCreado(
                JugadorId.of("xxx-fff"),
                new Nombre("Raul .A Alzate"),
                new Color("Red")
        );
        event.setAggregateRootId("ggg-ggg");

        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxx-ffff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());

        var carroCreado = (CarroCreado) eventCaptor.getAllValues().get(0);
        Assertions.assertEquals("Red", carroCreado.getColor().value());
        Assertions.assertTrue(Objects.nonNull(carroCreado.getPlaca()));
    }
}