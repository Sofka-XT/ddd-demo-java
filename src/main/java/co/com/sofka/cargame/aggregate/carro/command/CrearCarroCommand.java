package co.com.sofka.cargame.aggregate.carro.command;

import co.com.sofka.cargame.aggregate.carro.values.CarroId;
import co.com.sofka.cargame.aggregate.carro.values.Color;
import co.com.sofka.domain.generic.Command;

public class CrearCarroCommand implements Command {
    private Color color;
    private CarroId carroId;

    public CrearCarroCommand() {
    }

    public CrearCarroCommand(CarroId carroId, Color color) {
        this.carroId = carroId;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public CarroId getPlaca() {
        return carroId;
    }
}
