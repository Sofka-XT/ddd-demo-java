package co.com.sofka.cargame.domain.carro.events;

import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class ConductorAsignado extends DomainEvent implements Incremental {
    private final String nombre;
    private final Cedula cedula;

    public ConductorAsignado(String nombre, Cedula cedula) {
        super("carro.ConductorAsignado");
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public Cedula getCedula() {
        return cedula;
    }
}
