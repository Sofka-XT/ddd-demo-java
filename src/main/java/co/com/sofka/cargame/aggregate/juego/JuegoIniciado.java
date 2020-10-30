package co.com.sofka.cargame.aggregate.juego;

import co.com.sofka.domain.generic.DomainEvent;

public class JuegoIniciado extends DomainEvent {
    public JuegoIniciado() {
        super("sofka.cargame.JuegoIniciado");
    }
}
