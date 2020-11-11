package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.Jugador;
import co.com.sofka.cargame.domain.juego.events.JuegoFinalizado;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.cargame.domain.juego.values.Podio;
import co.com.sofka.cargame.usecase.UseCaseHandleBaseTest;
import org.junit.jupiter.api.Test;


class NotificarGanadoresUseCaseTest extends UseCaseHandleBaseTest {
    @Test
    void imprimierResultados_casoFeliz() {
        var useCase = new NotificarGanadoresUseCase();
        var podio = new Podio();
        podio = podio.asignarPrimerLugar(new Jugador(JugadorId.of("xxxx"), new Nombre("raul"), new Color("Red")));
        podio = podio.asignarSegundoLugar(new Jugador(JugadorId.of("ffff"), new Nombre("andres"), new Color("Green")));
        podio = podio.asignarTercerLugar(new Jugador(JugadorId.of("ggggg"), new Nombre("alzate"), new Color("Black")));
        var event = new JuegoFinalizado(podio);

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);


    }
}