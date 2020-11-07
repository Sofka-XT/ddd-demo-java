package co.com.sofka.cargame.domain.carril.events;

import co.com.sofka.cargame.domain.carril.values.Posicion;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class CarroDesplazado extends DomainEvent implements Incremental {
    private final Posicion posicion;

    public CarroDesplazado(Posicion posicion) {
        super("carril.CarroDesplazado");
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }
}
