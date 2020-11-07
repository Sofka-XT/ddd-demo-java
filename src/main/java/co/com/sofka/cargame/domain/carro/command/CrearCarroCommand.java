package co.com.sofka.cargame.domain.carro.command;

import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.Command;

public class CrearCarroCommand implements Command {
    private Color color;
    private CarroId carroId;
    private JuegoId juegoId;

    public void setColor(Color color) {
        this.color = color;
    }

    public CarroId getCarroId() {
        return carroId;
    }

    public void setCarroId(CarroId carroId) {
        this.carroId = carroId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(JuegoId juegoId) {
        this.juegoId = juegoId;
    }

    public CrearCarroCommand() {
    }

    public CrearCarroCommand(CarroId carroId, JuegoId juegoId, Color color) {
        this.carroId = carroId;
        this.color = color;
        this.juegoId = juegoId;
    }

    public Color getColor() {
        return color;
    }

    public CarroId getPlaca() {
        return carroId;
    }
}
