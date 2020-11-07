package co.com.sofka.cargame.domain.carro;


import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.domain.generic.Entity;

import java.util.Objects;
import java.util.Random;

public class Conductor extends Entity<Cedula> {
    private final String nombre;

    public Conductor(Cedula cc, String nombre) {
        super(cc);
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
    }

    public String nombre() {
        return nombre;
    }

    public Integer lanzarDado() {
        var rn = new Random();
        return 1 + rn.nextInt(6);
    }

}
