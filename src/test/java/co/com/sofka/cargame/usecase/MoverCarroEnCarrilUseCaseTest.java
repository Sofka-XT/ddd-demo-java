package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.aggregate.carril.events.CarroAgregadoACarrail;
import co.com.sofka.cargame.aggregate.carril.events.CarroDesplazado;
import co.com.sofka.cargame.aggregate.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.aggregate.carril.values.Posicion;
import co.com.sofka.cargame.aggregate.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.aggregate.carril.events.CarrilCreado;
import co.com.sofka.cargame.aggregate.carril.values.CarrilId;
import co.com.sofka.cargame.aggregate.carro.values.CarroId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MoverCarroEnCarrilUseCaseTest extends UseCaseHandleBaseTest {

    @Test
    void moverCarroEnCarril_casoFeliz(){
        var usecase = new MoverCarroEnCarrilUseCase();
        var event =   new KilometrajeCambiado(20, CarrilId.of("xxxx-xxxx"));

        when(repository.getEventsBy(anyString())).thenReturn(List.of(
                new CarrilCreado(500),
                new CarroAgregadoACarrail(CarroId.of("fffff-ffff"))
        ));

        usecase.addRepository(repository);

        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxx-xxxx")
                .asyncExecutor(usecase, new TriggeredEvent<>(event))
                .subscribe(subscriber);

        verify(subscriber).onNext(eventCaptor.capture());
        Assertions.assertTrue(eventCaptor.getValue() instanceof CarroDesplazado);
    }


    @Test
    public void alcazarLaMeta_casoFeliz(){
        var usecase = new MoverCarroEnCarrilUseCase();
        var event =   new KilometrajeCambiado(101, CarrilId.of("xxxx-xxxx"));

        when(repository.getEventsBy(anyString())).thenReturn(List.of(
                new CarrilCreado(500),
                new CarroAgregadoACarrail(CarroId.of("fffff-ffff")),
                new CarroDesplazado(new Posicion(300, 500)),
                new CarroDesplazado(new Posicion(100, 500))
        ));

        usecase.addRepository(repository);

        UseCaseHandler.getInstance()
                .setIdentifyExecutor("xxxx-xxxx")
                .asyncExecutor(usecase, new TriggeredEvent<>(event))
                .subscribe(subscriber);

        verify(subscriber, times(2)).onNext(eventCaptor.capture());

        var events = eventCaptor.getAllValues();
        Assertions.assertTrue(events.get(0) instanceof CarroDesplazado);
        Assertions.assertTrue(events.get(1) instanceof CarroFinalizoSuRecorrido);

    }

}