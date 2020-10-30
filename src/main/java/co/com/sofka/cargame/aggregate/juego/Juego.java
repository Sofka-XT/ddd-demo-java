package co.com.sofka.cargame.aggregate.juego;

import co.com.sofka.cargame.aggregate.juego.events.*;
import co.com.sofka.cargame.aggregate.juego.values.*;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;
import java.util.Set;

public class Juego extends AggregateEvent<JuegoId> {

    protected Set<Jugador> jugadores;
    protected Pista pista;
    protected Boolean jugando;
    protected Podio podio;

    public Juego(JuegoId id, Pista pista) {
        super(id);
        appendChange(new JuegoCreado(pista)).apply();
    }

    private Juego(JuegoId id) {
        super(id);
        subscribe(new JuegoEventChange(this));
    }

    public static Juego from(JuegoId id, List<DomainEvent> events) {
        var juego = new Juego(id);
        events.forEach(juego::applyEvent);
        return juego;
    }

    public void crearJugado(Nombre nombre) {
        appendChange(new JugadorCreado(nombre)).apply();
    }

    public void asignarPrimerLugar(JugadorId jugadorId) {
        appendChange(new PrimerLugarAsignado(jugadorId)).apply();
    }

    public void asignarSegundoLugarLugar(JugadorId jugadorId) {
        appendChange(new SegundoLugarAsignado(jugadorId)).apply();
    }

    public void asignarTercerLugar(JugadorId jugadorId) {
        appendChange(new TercerLugarAsignado(jugadorId)).apply();
        appendChange(new JuegoFinalizado()).apply();
    }

    public void iniciarJuego() {
        appendChange(new JuegoIniciado()).apply();
    }

    public Set<Jugador> jugadores() {
        return jugadores;
    }

    public Pista.Values pista() {
        return pista.value();
    }

    public Boolean getJugando() {
        return jugando;
    }

    public Podio.Props getPodio() {
        return podio.value();
    }
}
