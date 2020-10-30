package co.com.sofka.cargame.aggregate.juego.events;

import co.com.sofka.cargame.aggregate.juego.values.Pista;
import co.com.sofka.domain.generic.DomainEvent;

public class JuegoCreado extends DomainEvent {
    private final Pista pista;


    public JuegoCreado(Pista pista) {
        super("sofka.cargame.JuegoCreado");
        this.pista = pista;
    }

    public Pista getPista() {
        return pista;
    }

}
