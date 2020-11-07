package co.com.sofka.cargame.domain.carril;

import co.com.sofka.cargame.domain.carril.events.CarrilCreado;
import co.com.sofka.cargame.domain.carril.events.CarroAgregadoACarrail;
import co.com.sofka.cargame.domain.carril.events.CarroDesplazado;
import co.com.sofka.cargame.domain.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.domain.carril.values.Posicion;
import co.com.sofka.domain.generic.EventChange;

import java.util.Optional;

public class CarrilEventChange extends EventChange {
    public CarrilEventChange(Carril carril) {
        apply((CarrilCreado event) -> {
            carril.metros = event.getMetros();
            carril.juegoId = event.getJuegoId();
            carril.desplazamientoFinal = false;
            carril.postion = new Posicion(0, 0);
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
