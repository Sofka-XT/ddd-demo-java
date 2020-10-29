package co.com.sofka.cargame.carro.events;

import co.com.sofka.cargame.carro.values.Cedula;
import co.com.sofka.domain.generic.DomainEvent;

public class ConductorAsignado extends DomainEvent {
    private final String nombre;
    private final Cedula cedula;

    public String getNombre() {
        return nombre;
    }

    public Cedula getCedula() {
        return cedula;
    }

    public ConductorAsignado(String nombre, Cedula cedula) {
        super("cargame.car.ConductorAsignado");
        this.nombre = nombre;
        this.cedula = cedula;
    }
}
