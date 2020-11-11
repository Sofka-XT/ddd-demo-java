package co.com.sofka.cargame.domain.juego.command;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.Command;

public class InicarJuegoCommand implements Command {
    private JuegoId juegoId;

    public InicarJuegoCommand() {
    }

    public InicarJuegoCommand(JuegoId juegoId) {
        this.juegoId = juegoId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(JuegoId juegoId) {
        this.juegoId = juegoId;
    }
}
