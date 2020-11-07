package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class TercerLugarAsignado extends DomainEvent implements Incremental {
    private final JugadorId jugadorId;

    public TercerLugarAsignado(JugadorId jugadorId) {
        super("juego.TercerLugarAsignado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
