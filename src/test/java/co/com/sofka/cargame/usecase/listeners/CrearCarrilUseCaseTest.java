package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carril.events.CarrilCreado;
import co.com.sofka.cargame.domain.carril.events.CarroAgregadoACarrail;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.UseCaseHandleBaseTest;
import co.com.sofka.cargame.usecase.services.JuegoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearCarrilUseCaseTest extends UseCaseHandleBaseTest {

    @Mock
    private JuegoService juegoService;

    @Test
    void crearCarril_casoFeliz() throws InterruptedException {
        var useCase = new CrearCarrilUseCase();
        var event = new CarroCreado(CarroId.of("ssss"), JuegoId.of("ggggg"), new Color("Red"));
        useCase.addServiceBuilder(new ServiceBuilder().addService(juegoService));
        when(juegoService.getKilometros(any())).thenReturn(3);

        //act
        UseCaseHandler.getInstance()
                .setIdentifyExecutor("fff-aas-fff")
                .asyncExecutor(useCase, new TriggeredEvent<>(event))
                .subscribe(subscriber);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());
        Thread.sleep(500);
        verify(juegoService).getKilometros(any());
        var carrilCreado = (CarrilCreado) eventCaptor.getAllValues().get(0);
        var carroAgregadoACarrail = (CarroAgregadoACarrail) eventCaptor.getAllValues().get(1);

    }
}