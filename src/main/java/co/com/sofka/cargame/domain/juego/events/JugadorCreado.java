package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class JugadorCreado extends DomainEvent implements Incremental {
    private final Nombre nombre;
    private final Color color;
    private final JugadorId jugadorId;


    public JugadorCreado(JugadorId jugadorId, Nombre nombre, Color color) {
        super("juego.JugadorCreado");
        this.nombre = nombre;
        this.jugadorId = jugadorId;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }

    public Nombre getNombre() {
        return nombre;
    }

}
