package co.com.sofka.cargame.aggregate.carril;

import co.com.sofka.cargame.aggregate.carril.events.CarrilCreado;
import co.com.sofka.cargame.aggregate.carril.events.CarroAgregadoACarrail;
import co.com.sofka.cargame.aggregate.carril.events.CarroDesplazado;
import co.com.sofka.cargame.aggregate.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.aggregate.carril.values.Posicion;
import co.com.sofka.domain.generic.EventChange;

import java.util.Optional;

public class CarrilEventChange extends EventChange {
    public CarrilEventChange(Carril carril) {
        apply((CarrilCreado event) -> {
            carril.metros = event.getMetros();
            carril.desplazamientoFinal = false;
        });

        apply((CarroAgregadoACarrail event) -> {
            carril.carroId = event.getCarroId();
            carril.postion = new Posicion(0, carril.metros);
        });

        apply((CarroDesplazado event) -> {
            var actual = Optional.of(event.getPosicion())
                    .map(Posicion::value)
                    .map(Posicion.Props::actual)
                    .orElseThrow() + carril.posicionActual();
            carril.postion = new Posicion(actual, carril.metros);
        });

        apply((CarroFinalizoSuRecorrido event) -> {
            carril.desplazamientoFinal = true;
            carril.postion = new Posicion(0, carril.metros);
        });

    }
}
