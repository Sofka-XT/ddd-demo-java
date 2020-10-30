package co.com.sofka.cargame.carril.events;

import co.com.sofka.cargame.carril.values.Posicion;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroDesplazado extends DomainEvent {
    private final Posicion posicion;

    public Posicion getPosicion() {
        return posicion;
    }

    public CarroDesplazado(Posicion posicion) {
        super("cargame.carril.CarroDesplazado");
        this.posicion = posicion;
    }
}
