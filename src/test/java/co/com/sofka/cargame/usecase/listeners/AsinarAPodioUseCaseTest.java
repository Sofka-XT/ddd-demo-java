package co.com.sofka.cargame.usecase.listeners;


import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.events.*;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.UseCaseHandleBaseTest;
import co.com.sofka.cargame.usecase.services.CarroService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AsinarAPodioUseCaseTest extends UseCaseHandleBaseTest {

    @Mock
    private CarroService carroService;

    @Test
    void asignarPidioPrimerLugar_casoFeliz() throws InterruptedException {
        var useCase = new AsinarAPodioUseCase();
        var event = new CarroFinalizoSuRecorrido(CarroId.of("dddd"), JuegoId.of("fffff"));
        event.setAggregateRootId("hhhh");

        when(repository.getEventsBy(anyString(), anyString())).thenReturn(List.of(
                new JuegoCreado(new Pista(3, 3)),
                new JugadorCreado(JugadorId.of("1"), new Nombre("raul"), new Color("Red")),
                new JugadorCreado(JugadorId.of("2"), new Nombre("andres"), new Color("Blue")),
                new JugadorCreado(JugadorId.of("3"), new Nombre("alzate"), new Color("Green")),
                new JuegoIniciado()
        ));
        when(carroService.getConductorIdPor(any())).thenReturn("1");

        useCase.addRepository(repository);
        useCase.addServiceBuilder(new ServiceBuilder().addService(carroService));

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);
        Thread.sleep(500);

        verify(subscriber).onNext(eventCaptor.capture());
        var primerLugarAsignado = (PrimerLugarAsignado) eventCaptor.getValue();
        Assertions.assertEquals("1", primerLugarAsignado.getJugadorId().value());

    }

    @Test
    void asignarPidioSegundoLugar_casoFeliz() throws InterruptedException {
        var useCase = new AsinarAPodioUseCase();
        var event = new CarroFinalizoSuRecorrido(CarroId.of("dddd"), JuegoId.of("fffff"));
        event.setAggregateRootId("hhhh");

        when(repository.getEventsBy(anyString(), anyString())).thenReturn(List.of(
                new JuegoCreado(new Pista(3, 3)),
                new JugadorCreado(JugadorId.of("1"), new Nombre("raul"), new Color("Red")),
                new JugadorCreado(JugadorId.of("2"), new Nombre("andres"), new Color("Blue")),
                new JugadorCreado(JugadorId.of("3"), new Nombre("alzate"), new Color("Green")),
                new JuegoIniciado(),
                new PrimerLugarAsignado(JugadorId.of("1"))
        ));
        when(carroService.getConductorIdPor(any())).thenReturn("2");

        useCase.addRepository(repository);
        useCase.addServiceBuilder(new ServiceBuilder().addService(carroService));

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);
        Thread.sleep(500);

        verify(subscriber).onNext(eventCaptor.capture());
        var segundoLugarAsignado = (SegundoLugarAsignado) eventCaptor.getValue();
        Assertions.assertEquals("2", segundoLugarAsignado.getJugadorId().value());

    }

    @Test
    void asignarPidioTercerLugar_casoFeliz() throws InterruptedException {
        var useCase = new AsinarAPodioUseCase();
        var event = new CarroFinalizoSuRecorrido(CarroId.of("dddd"), JuegoId.of("fffff"));
        event.setAggregateRootId("hhhh");

        when(repository.getEventsBy(anyString(), anyString())).thenReturn(List.of(
                new JuegoCreado(new Pista(3, 3)),
                new JugadorCreado(JugadorId.of("1"), new Nombre("raul"), new Color("Red")),
                new JugadorCreado(JugadorId.of("2"), new Nombre("andres"), new Color("Blue")),
                new JugadorCreado(JugadorId.of("3"), new Nombre("alzate"), new Color("Green")),
                new JuegoIniciado(),
                new PrimerLugarAsignado(JugadorId.of("1")),
                new SegundoLugarAsignado(JugadorId.of("2"))
        ));
        when(carroService.getConductorIdPor(any())).thenReturn("3");

        useCase.addRepository(repository);
        useCase.addServiceBuilder(new ServiceBuilder().addService(carroService));

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);
        Thread.sleep(500);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());
        var tercerLugarAsignado = (TercerLugarAsignado) eventCaptor.getAllValues().get(0);
        var juegoFinalizado = (JuegoFinalizado) eventCaptor.getAllValues().get(1);
        Assertions.assertEquals("3", tercerLugarAsignado.getJugadorId().value());
        Assertions.assertTrue(Objects.nonNull(juegoFinalizado.getPodio()));

    }
}