package co.com.sofka.cargame.car.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Color implements ValueObject<String> {
    private final String value;

    public Color(String value) {
        //TODO: colocar un enum
        this.value = Objects.requireNonNull(value, "El color no puede ser null");
    }

    @Override
    public String value() {
        return value;
    }
}
