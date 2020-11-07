package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.juego.values.Podio;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class JuegoFinalizado extends DomainEvent implements Incremental {
    private final Podio podio;

    public Podio getPodio() {
        return podio;
    }

    public JuegoFinalizado(Podio podio) {
        super("juego.JuegoFinalizado");
        this.podio = podio;
    }
}
