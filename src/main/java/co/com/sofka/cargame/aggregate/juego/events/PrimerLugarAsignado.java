package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.cargame.aggregate.juego.values.JugadorId;
import co.com.sofka.domain.generic.DomainEvent;

public class PrimerLugarAsignado extends DomainEvent {
    private final JugadorId jugadorId;

    public PrimerLugarAsignado(JugadorId jugadorId) {
        super("sofka.cargame.PrimerLugarAsignado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
