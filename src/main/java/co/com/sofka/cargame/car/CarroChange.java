package co.com.sofka.cargame.car;

import co.com.sofka.cargame.car.events.CarroCreado;
import co.com.sofka.cargame.car.events.ConductorAsignado;
import co.com.sofka.cargame.car.events.KilometrajeCambiado;
import co.com.sofka.domain.generic.EventChange;

import java.util.Objects;

public class CarroChange extends EventChange {
    public CarroChange(Carro carro) {

        apply((CarroCreado event) -> {
            carro.color = event.getColor();
            carro.distancia = 0;
        });

        apply((KilometrajeCambiado event) -> {
            var distancia = event.getDistancia();
            Objects.requireNonNull(distancia, "La distancia no puede ser null");
            if(distancia <= 0) {
                throw new IllegalArgumentException("No puede ser negativo o cero el valod de la distancia");
            }
            carro.distancia = carro.distancia + distancia;
        });

        apply((ConductorAsignado event) -> {
            carro.conductor  = new Conductor(event.getCedula(), event.getNombre());
        });


    }
}
