package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.cargame.aggregate.juego.values.JugadorId;
import co.com.sofka.domain.generic.DomainEvent;

public class TercerLugarAsignado extends DomainEvent {
    private final JugadorId jugadorId;

    public TercerLugarAsignado(JugadorId jugadorId) {
        super("sofka.cargame.TercerLugarAsignado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
