package co.com.sofka.cargame.aggregate.juego;


import co.com.sofka.cargame.aggregate.juego.values.JugadorId;
import co.com.sofka.cargame.aggregate.juego.values.Nombre;
import co.com.sofka.domain.generic.Entity;

public class Jugador extends Entity<JugadorId> {
    private final Nombre nombre;

    public Jugador(JugadorId entityId, Nombre nombre) {
        super(entityId);
        this.nombre = nombre;
    }

    public Nombre nombre() {
        return nombre;
    }

}
