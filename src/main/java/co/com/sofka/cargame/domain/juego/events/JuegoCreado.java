package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.domain.generic.DomainEvent;

public class JuegoCreado extends DomainEvent {
    private final Pista pista;


    public JuegoCreado(Pista pista) {
        super("juego.JuegoCreado");
        this.pista = pista;
    }

    public Pista getPista() {
        return pista;
    }

}
