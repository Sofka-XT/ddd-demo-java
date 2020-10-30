package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.cargame.aggregate.juego.values.JugadorId;
import co.com.sofka.domain.generic.DomainEvent;

public class SegundoLugarAsignado extends DomainEvent {
    private final JugadorId jugadorId;

    public SegundoLugarAsignado(JugadorId jugadorId) {
        super("sofka.cargame.SegundoLugarAsignado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
