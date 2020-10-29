package co.com.sofka.cargame.carro.events;

import co.com.sofka.cargame.carro.values.Color;
import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroCreado extends DomainEvent {
    private final CarroId carroId;
    private final Color color;

    public CarroCreado(CarroId carroId, Color color) {
        super("cargame.car.CarroCreado");
        this.carroId = carroId;
        this.color = color;
    }

    public CarroId getPlaca() {
        return carroId;
    }

    public Color getColor() {
        return color;
    }
}
