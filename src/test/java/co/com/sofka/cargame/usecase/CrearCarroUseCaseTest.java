package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.aggregate.carro.command.CrearCarroCommand;
import co.com.sofka.cargame.aggregate.carro.events.CarroCreado;
import co.com.sofka.cargame.aggregate.carro.values.CarroId;
import co.com.sofka.cargame.aggregate.carro.values.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class CrearCarroUseCaseTest extends UseCaseHandleBaseTest{
    @Test
    void crearCarro_casoFiles(){
        var useCase = new CrearCarroUseCase();
        var command = new CrearCarroCommand(CarroId.of("XF332"), new Color("Red"));
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxxx")
                .asyncExecutor(useCase, new RequestCommand<>(command))
                .subscribe(subscriber);

        verify(subscriber).onNext(eventCaptor.capture());

        var event = (CarroCreado)eventCaptor.getValue();
        Assertions.assertEquals("Red", event.getColor().value());
        Assertions.assertEquals("XF332", event.getPlaca().value());
    }
}