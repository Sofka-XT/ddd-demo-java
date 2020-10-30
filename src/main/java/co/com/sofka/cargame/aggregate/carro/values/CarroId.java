package co.com.sofka.cargame.aggregate.carro.values;

import co.com.sofka.domain.generic.Identity;

public class CarroId extends Identity {
    public CarroId(String value) {
        super(value);
    }

    public CarroId() {
    }

    public static CarroId of(String value) {
        return new CarroId(value);
    }
}
