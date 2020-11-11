package co.com.sofka.cargame.domain.juego;

import co.com.sofka.cargame.domain.juego.events.*;
import co.com.sofka.cargame.domain.juego.values.Podio;
import co.com.sofka.domain.generic.EventChange;

import java.util.HashMap;


public class JuegoEventChange extends EventChange {
    public JuegoEventChange(Juego juego) {
        apply((JuegoCreado event) -> {
            juego.pista = event.getPista();
            juego.jugando = false;
            juego.jugadores = new HashMap<>();
            juego.podio = new Podio();
        });
        apply((JugadorCreado event) -> {
            if (!juego.jugando) {
                var id = event.getJugadorId();
                juego.jugadores.put(id, new Jugador(id, event.getNombre(), event.getColor()));
            } else {
                throw new IllegalArgumentException("No puede asignar un jugado si esta en juego");
            }
        });
        apply((JuegoFinalizado event) -> {
            juego.jugando = false;
        });
        apply((PrimerLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = juego.jugadores.get(event.getJugadorId());
                juego.podio = juego.podio.asignarPrimerLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio no esta en marcha el juego");
            }
        });
        apply((SegundoLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = juego.jugadores.get(event.getJugadorId());
                juego.podio = juego.podio.asignarSegundoLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio no esta en marcha el juego");
            }
        });
        apply((TercerLugarAsignado event) -> {
            if (juego.jugando) {
                Jugador jugadorGanador = juego.jugadores.get(event.getJugadorId());
                juego.podio = juego.podio.asignarTercerLugar(jugadorGanador);
            } else {
                throw new IllegalArgumentException("No puede asignar al podio no esta en marcha el juego");
            }
        });

        apply((JuegoIniciado event) -> {
            juego.jugando = true;
        });
    }


}
