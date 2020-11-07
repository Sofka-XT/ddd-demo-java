package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Pista implements ValueObject<Pista.Values> {
    private final Integer kilometros;
    private final Integer numeroDeCarriles;

    public Pista(Integer kilometros, Integer numeroDeCarriles) {
        this.kilometros = Objects.requireNonNull(kilometros, "Kilomentros requeridos");
        this.numeroDeCarriles = Objects.requireNonNull(numeroDeCarriles, "Numero de carriles necesarios");
        if(this.kilometros <= 0) {
            throw new IllegalArgumentException("No se puede agregar un kilometraje 0 o negativo");
        }
        if(this.numeroDeCarriles <= 0) {
            throw new IllegalArgumentException("No se puede agregar un numero de carriles 0 o negativo");
        }
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
