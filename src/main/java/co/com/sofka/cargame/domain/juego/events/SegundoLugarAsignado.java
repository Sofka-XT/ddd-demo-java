package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class SegundoLugarAsignado extends DomainEvent implements Incremental {
    private final JugadorId jugadorId;

    public SegundoLugarAsignado(JugadorId jugadorId) {
        super("juego.SegundoLugarAsignado");
        this.jugadorId = jugadorId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
