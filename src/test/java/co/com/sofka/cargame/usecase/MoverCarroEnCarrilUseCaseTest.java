package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.carril.CarrilCreado;
import co.com.sofka.cargame.carril.CarrilId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MoverCarroEnCarrilUseCaseTest extends UseCaseHandleBaseTest {

    @Test
    void moverCarroEnCarril_casoFeliz(){
        var usecase = new MoverCarroEnCarrilUseCase();
        var event =   new KilometrajeCambiado(20, CarrilId.of("xxxx-xxxx"));

        when(repository.getEventsBy(anyString())).thenReturn(List.of(
                new CarrilCreado()
        ));

        usecase.addRepository(repository);

        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxx-xxxx")
                .asyncExecutor(usecase, new TriggeredEvent<>(event))
                .subscribe(subscriber);

        verify(subscriber).onNext(eventCaptor.capture());


    }
}