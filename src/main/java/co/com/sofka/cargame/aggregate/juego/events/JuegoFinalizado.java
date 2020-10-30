package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.domain.generic.DomainEvent;

public class JuegoFinalizado extends DomainEvent {
    public JuegoFinalizado() {
        super("sofka.cargame.JuegoFinalizado");
    }
}
