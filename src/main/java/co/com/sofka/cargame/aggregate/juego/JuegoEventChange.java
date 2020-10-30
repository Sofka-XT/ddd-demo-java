package co.com.sofka.cargame.aggregate.juego;

import co.com.sofka.cargame.aggregate.juego.events.*;
import co.com.sofka.cargame.aggregate.juego.values.JugadorId;
import co.com.sofka.cargame.aggregate.juego.values.Podio;
import co.com.sofka.domain.generic.EventChange;
import co.com.sofka.domain.generic.Identity;

import java.util.HashSet;

public class JuegoEventChange extends EventChange {
    public JuegoEventChange(Juego juego) {
        apply((JuegoCreado event) -> {
            juego.pista = event.getPista();
            juego.jugando = false;
            juego.jugadores = new HashSet<>();
            juego.podio = new Podio();
        });
        apply((JugadorCreado event) -> {
            if (!juego.jugando) {
                juego.jugadores.add(new Jugador(new JugadorId(), event.getNombre()));
            } else {
                throw new IllegalArgumentException("No puede asignar un jugado si esta en juego");
            }
        });
        apply((JuegoFinalizado event) -> {
            juego.jugando = false;
        });
        apply((PrimerLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = getJugador(juego, event.getJugadorId());
                juego.podio = juego.podio.asignarPrimerLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio");
            }
        });
        apply((SegundoLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = getJugador(juego, event.getJugadorId());
                juego.podio = juego.podio.asignarSegundoLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio");
            }
        });
        apply((TercerLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = getJugador(juego, event.getJugadorId());
                juego.podio = juego.podio.asignarTercerLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio");
            }
        });

        apply((JuegoIniciado event) -> {
            juego.jugando = true;
        });
    }

    private Jugador getJugador(Juego juego, Identity id) {
        return juego.jugadores
                .stream()
                .filter(jugador -> jugador.identity().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El jugador no existe"));
    }
}
