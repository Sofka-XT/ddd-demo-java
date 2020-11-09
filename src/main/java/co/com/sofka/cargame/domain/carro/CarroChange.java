package co.com.sofka.cargame.domain.carro;

import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.carro.events.ConductorAsignado;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;
import co.com.sofka.domain.generic.EventChange;

import java.util.Objects;

public class CarroChange extends EventChange {
    public CarroChange(Carro carro) {

        apply((CarroCreado event) -> {
            carro.color = event.getColor();
            carro.distancia = 0;
            carro.juegoId = event.getJuegoId();
        });

        apply((KilometrajeCambiado event) -> {
            var distancia = Objects.requireNonNull(event.getDistancia(), "La distancia no puede ser null");
            if (distancia <= 0) {
                throw new IllegalArgumentException("No puede ser negativo o cero el valod de la distancia");
            }
            carro.distancia = carro.distancia + distancia;
        });

        apply((ConductorAsignado event) -> {
            carro.conductor = new Conductor(event.getCedula(), event.getNombre());
        });


    }
}
