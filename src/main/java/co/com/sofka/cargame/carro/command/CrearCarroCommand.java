package co.com.sofka.cargame.carro.command;

import co.com.sofka.cargame.carro.values.Color;
import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.Command;

public class CrearCarroCommand implements Command {
    private  Color color;
    private CarroId carroId;

    public Color getColor() {
        return color;
    }

    public CarroId getPlaca() {
        return carroId;
    }

    public CrearCarroCommand(){}

    public CrearCarroCommand(CarroId carroId, Color color) {
        this.carroId = carroId;
        this.color = color;
    }
}
