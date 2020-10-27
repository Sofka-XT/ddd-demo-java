package co.com.sofka.cargame.car.usecase;


import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.car.command.AsignarConductorCommand;
import co.com.sofka.cargame.car.events.CarroCreado;
import co.com.sofka.cargame.car.events.ConductorAsignado;
import co.com.sofka.cargame.car.events.KilometrajeCambiado;
import co.com.sofka.cargame.car.values.Cedula;
import co.com.sofka.cargame.car.values.Color;
import co.com.sofka.cargame.car.values.CarroId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AsignarConductorUseCaseTest extends UseCaseHandleBaseTest{

    @Test
    void asignarCodunctorTest_happyPath(){

        var useCase = new AsignarConductorUseCase();
        when(repository.getEventsBy("MX5124")).thenReturn( List.of(
                new CarroCreado(CarroId.of("MX5124"), new Color("rojo")),
                new KilometrajeCambiado(20),
                new KilometrajeCambiado(50)
        ));
        useCase.addRepository(repository);

        var request = new RequestCommand<>(new AsignarConductorCommand(
                "Raul Alzate", Cedula.of("111251142"), CarroId.of("MX5124")
        ));

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("MX5124")
                .asyncExecutor(useCase, request)
                .subscribe(subscriber);

        verify(subscriber).onNext(eventCaptor.capture());

        var conductorAsignado = (ConductorAsignado) eventCaptor.getAllValues().get(0);

        Assertions.assertEquals("Raul Alzate", conductorAsignado.getNombre());
        Assertions.assertEquals("111251142", conductorAsignado.getCedula().value());
    }

}