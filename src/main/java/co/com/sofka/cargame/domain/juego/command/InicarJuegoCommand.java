package co.com.sofka.cargame.domain.juego.command;

import co.com.sofka.domain.generic.Command;

public class InicarJuegoCommand extends Command {
    private String juegoId;

    public InicarJuegoCommand() {
    }

    public InicarJuegoCommand(String juegoId) {
        this.juegoId = juegoId;
    }

    public String getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(String juegoId) {
        this.juegoId = juegoId;
    }
}
