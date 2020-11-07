package co.com.sofka.cargame.domain.juego.command;

import co.com.sofka.domain.generic.Command;

import java.util.Map;

public class CrearJuegoCommand implements Command {
    private Integer kilometros;
    private Map<String, String> jugadores;

    public void setKilometros(Integer kilometros) {
        this.kilometros = kilometros;
    }


    public Map<String, String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Map<String, String> jugadores) {
        this.jugadores = jugadores;
    }

    public CrearJuegoCommand(){
    }
    public CrearJuegoCommand(Integer kilometros,  Map<String, String> jugadores) {
        this.kilometros = kilometros;
        this.jugadores = jugadores;

    }

    public Integer getKilometros() {
        return kilometros;
    }

}
