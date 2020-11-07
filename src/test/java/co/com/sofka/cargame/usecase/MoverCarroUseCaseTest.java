package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.command.MoverCarroCommand;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.carro.events.ConductorAsignado;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MoverCarroUseCaseTest extends UseCaseHandleBaseTest{
    @Test
    void moverCarro_casoFeliz(){
        var command = new MoverCarroCommand(CarroId.of("fffff"), CarrilId.of("xxxxxx"));
        var useCase = new MoverCarroUseCase();
        when(repository.getEventsBy("MX5124")).thenReturn( List.of(
                new CarroCreado(CarroId.of("MX5124"), JuegoId.of("xxxx"), new Color("rojo")),
                new ConductorAsignado("Raul A. Alzate", Cedula.of("3188452152")),
                new KilometrajeCambiado(20, CarrilId.of("xxxx-xxxx"))
        ));
        useCase.addRepository(repository);

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("MX5124")
                .asyncExecutor(useCase, new RequestCommand<>(command))
                .subscribe(subscriber);

        verify(subscriber).onNext(eventCaptor.capture());

        var kilometrajeCambiado = (KilometrajeCambiado) eventCaptor.getValue();
        Assertions.assertEquals("xxxxxx", kilometrajeCambiado.getCarrilId().value());
        Assertions.assertTrue(kilometrajeCambiado.getDistancia() > 0);
    }
}