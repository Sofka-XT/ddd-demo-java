package co.com.sofka.cargame.domain.juego;

import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.events.*;
import co.com.sofka.cargame.domain.juego.values.*;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;
import java.util.Map;


public class Juego extends AggregateEvent<JuegoId> {

    protected Map<JugadorId, Jugador> jugadores;
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

    public void crearJugador(JugadorId jugadorId, Nombre nombre, Color color) {
        appendChange(new JugadorCreado(jugadorId, nombre, color)).apply();
    }

    public void iniciarJuegoACompetidor(CarroId carroId, CarrilId carrilId){
        appendChange(new CompetidorIniciado(carroId, carrilId)).apply();
    }

    public void asignarPrimerLugar(JugadorId jugadorId) {
        appendChange(new PrimerLugarAsignado(jugadorId)).apply();
    }

    public void asignarSegundoLugar(JugadorId jugadorId) {
        appendChange(new SegundoLugarAsignado(jugadorId)).apply();
    }

    public void asignarTercerLugar(JugadorId jugadorId) {
        appendChange(new TercerLugarAsignado(jugadorId)).apply();
        appendChange(new JuegoFinalizado(podio)).apply();
    }

    public void iniciarJuego() {
        appendChange(new JuegoIniciado()).apply();
    }

    public Map<JugadorId, Jugador> jugadores() {
        return Map.copyOf(jugadores);
    }

    public Pista.Values pista() {
        return pista.value();
    }

    public Boolean jugando() {
        return jugando;
    }

    public Podio.Props podio() {
        return podio.value();
    }

    @Override
    public String toString() {
        return "Juego{" +
                "jugadores=" + jugadores +
                ", pista=" + pista +
                ", jugando=" + jugando +
                ", podio=" + podio +
                '}';
    }
}
