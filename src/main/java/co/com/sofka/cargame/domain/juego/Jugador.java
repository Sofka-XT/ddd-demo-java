package co.com.sofka.cargame.domain.juego;


import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.domain.generic.Entity;

public class Jugador extends Entity<JugadorId> {
    private final Nombre nombre;
    private final Color color;
    private Integer puntos;

    public Jugador(JugadorId entityId, Nombre nombre, Color color) {
        super(entityId);
        this.nombre = nombre;
        this.color = color;
        this.puntos = 0;
    }

    public Nombre nombre() {
        return nombre;
    }

    public Color color(){ return color;}

    public void asignarPuntos(Integer puntos){
        this.puntos  =+ puntos;
    }


}
