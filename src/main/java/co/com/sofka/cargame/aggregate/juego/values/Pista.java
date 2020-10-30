package co.com.sofka.cargame.aggregate.juego.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Pista implements ValueObject<Pista.Values> {
    private final Integer kilometros;
    private final Integer numeroDeCarriles;

    public Pista(Integer kilometros, Integer numeroDeCarriles) {
        this.kilometros = Objects.requireNonNull(kilometros, "Kilomentros requeridos");
        this.numeroDeCarriles = Objects.requireNonNull(numeroDeCarriles, "Numero de carriles necesarios");
    }

    @Override
    public Values value() {
        return new Values() {
            @Override
            public Integer kilometros() {
                return kilometros;
            }

            @Override
            public Integer numeroDeCarriles() {
                return numeroDeCarriles;
            }
        };
    }

    public interface Values {
        Integer kilometros();

        Integer numeroDeCarriles();
    }
}
