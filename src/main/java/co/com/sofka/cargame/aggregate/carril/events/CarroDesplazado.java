package co.com.sofka.cargame.aggregate.carril.events;

import co.com.sofka.cargame.aggregate.carril.values.Posicion;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroDesplazado extends DomainEvent {
    private final Posicion posicion;

    public CarroDesplazado(Posicion posicion) {
        super("cargame.carril.CarroDesplazado");
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }
}
