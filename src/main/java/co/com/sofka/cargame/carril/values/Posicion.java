package co.com.sofka.cargame.carril.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Posicion implements ValueObject<Posicion.Props> {
    private final Integer actual;
    private final Integer metal;

    public Posicion(Integer actual, Integer metal) {
        this.actual = Objects.requireNonNull(actual, "La ppsicion no puede ser null");
        this.metal = Objects.requireNonNull(metal, "La ppsicion no puede ser null");
        if(actual < 0) {
            throw new IllegalArgumentException("La posicion no puede ser negativa");
        }
        if(metal < 0) {
            throw new IllegalArgumentException("La metal no puede ser negativa");
        }
    }

    @Override
    public Props value() {
        return new Props() {
            @Override
            public Integer actual() {
                return actual;
            }

            @Override
            public Integer metal() {
                return metal;
            }
        };
    }

    public interface Props {
         Integer actual();
         Integer metal();
    }
}
