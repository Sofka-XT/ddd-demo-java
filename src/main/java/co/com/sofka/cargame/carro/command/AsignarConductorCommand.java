package co.com.sofka.cargame.carro.command;

import co.com.sofka.cargame.carro.values.Cedula;
import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.Command;

import java.util.Objects;

public class AsignarConductorCommand implements Command {
    private String nombre;
    private Cedula cedula;
    private CarroId carroId;

    public AsignarConductorCommand(String nombre, Cedula cedula, CarroId carroId) {
        this.nombre = Objects.requireNonNull(nombre, "No se puede tener el nombre nulo");
        this.cedula = cedula;
        this.carroId = carroId;
    }

    public AsignarConductorCommand(){

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cedula getCedula() {
        return cedula;
    }

    public void setCedula(Cedula cedula) {
        this.cedula = cedula;
    }

    public CarroId getPlaca() {
        return carroId;
    }

    public void setPlaca(CarroId carroId) {
        this.carroId = carroId;
    }
}
