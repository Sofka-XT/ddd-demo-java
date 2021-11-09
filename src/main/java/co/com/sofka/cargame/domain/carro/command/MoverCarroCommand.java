package co.com.sofka.cargame.domain.carro.command;

import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.domain.generic.Command;

public class MoverCarroCommand extends Command {
    private CarroId carroId;
    private CarrilId carrilId;

    public MoverCarroCommand() {
    }

    public MoverCarroCommand(CarroId carroId, CarrilId carrilId) {
        this.carroId = carroId;
        this.carrilId = carrilId;
    }

    public CarroId getCarroId() {
        return carroId;
    }

    public CarrilId getCarrilId() {
        return carrilId;
    }
}
