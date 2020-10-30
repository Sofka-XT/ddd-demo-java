package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.cargame.aggregate.juego.values.Nombre;
import co.com.sofka.domain.generic.DomainEvent;

public class JugadorCreado extends DomainEvent {
    private final Nombre nombre;


    public JugadorCreado(Nombre nombre) {
        super("sofka.cargame.JugadorCreado");
        this.nombre = nombre;
    }

    public Nombre getNombre() {
        return nombre;
    }

}
