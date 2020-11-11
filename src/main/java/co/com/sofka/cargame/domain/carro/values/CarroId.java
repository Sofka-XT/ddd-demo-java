package co.com.sofka.cargame.domain.carro.values;

import co.com.sofka.domain.generic.Identity;

public class CarroId extends Identity {

    public CarroId() {
    }

    private CarroId(String value) {
        super(value);
    }

    public static CarroId of(String value) {
        return new CarroId(value);
    }
}
