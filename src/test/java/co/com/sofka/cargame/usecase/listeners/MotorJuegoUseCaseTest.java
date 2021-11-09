package co.com.sofka.cargame.usecase.listeners;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.carro.events.ConductorAsignado;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.cargame.domain.juego.events.JuegoCreado;
import co.com.sofka.cargame.domain.juego.events.JuegoFinalizado;
import co.com.sofka.cargame.domain.juego.events.JuegoIniciado;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.domain.juego.values.Podio;
import co.com.sofka.cargame.usecase.MoverCarroUseCase;
import co.com.sofka.cargame.usecase.UseCaseHandleBaseTest;
import co.com.sofka.cargame.usecase.model.CarroSobreCarril;
import co.com.sofka.cargame.usecase.services.CarrilCarroService;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MotorJuegoUseCaseTest extends UseCaseHandleBaseTest {
    private static final String JUEGO_ID = "fff-aaa-gggg";

    @Mock
    private CarrilCarroService carrilCarroService;

    @Spy
    private MoverCarroUseCase moverCarroUseCase;

    @BeforeEach
    public void setup() {
        var repository = mock(DomainEventRepository.class);
        when(repository.getEventsBy(eq("1"))).thenReturn(List.of(
                new CarroCreado(CarroId.of("1"), JuegoId.of(JUEGO_ID), new Color("RED")),
                new ConductorAsignado("Raul", Cedula.of("22222"))
        ));

        moverCarroUseCase.addRepository(repository);
    }

    @Test
    void correrJuego() {
        var juegoIniciado = new JuegoIniciado();
        juegoIniciado.setAggregateRootId(JUEGO_ID);

        when(carrilCarroService.getCarrosSobreCarriles(any())).thenReturn(List.of(
                new CarroSobreCarril("1", "1")
        ));


        when(repository.getEventsBy(anyString())).thenAnswer(new Answer<List<DomainEvent>>() {
            int counter = 0;

            @Override
            public List<DomainEvent> answer(InvocationOnMock invocationOnMock) {
                if (counter++ == 2) {
                    return List.of(
                            new JuegoCreado(new Pista(3, 2)),
                            new JuegoIniciado(),
                            new JuegoFinalizado(new Podio())
                    );
                }
                return List.of(
                        new JuegoCreado(new Pista(3, 2)),
                        new JuegoIniciado()
                );
            }
        });

        var motorJuegoUseCase = new MotorJuegoUseCase();
        motorJuegoUseCase.addRepository(repository);
        motorJuegoUseCase.addServiceBuilder(
                new ServiceBuilder()
                        .addService(carrilCarroService)
        );

        UseCaseHandler.getInstance()
                .setIdentifyExecutor(JUEGO_ID)
                .asyncExecutor(motorJuegoUseCase, new TriggeredEvent<>(juegoIniciado))
                .subscribe(subscriber);

        verify(subscriber, times(3)).onNext(eventCaptor.capture());
        verify(carrilCarroService).getCarrosSobreCarriles(any());
        Assertions.assertTrue(eventCaptor.getAllValues()
                .stream()
                .map(v -> (KilometrajeCambiado) v)
                .map(KilometrajeCambiado::getDistancia)
                .reduce(Integer::sum).orElseThrow() > 100
        );
    }
}