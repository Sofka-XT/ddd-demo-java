package co.com.sofka.cargame.carro;

import co.com.sofka.cargame.carro.events.CarroCreado;
import co.com.sofka.cargame.carro.events.ConductorAsignado;
import co.com.sofka.cargame.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.carro.values.Cedula;
import co.com.sofka.cargame.carro.values.Color;
import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.cargame.carril.values.CarrilId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class Carro extends AggregateEvent<CarroId> {

    protected Conductor conductor;
    protected Integer distancia;
    protected Color color;

    public Carro(CarroId carroId, Color color) {
        super(carroId);
        appendChange(new CarroCreado(carroId, color)).apply();
    }

    private Carro(CarroId carroId){
        super(carroId);
        subscribe(new CarroChange(this));
    }

    public static Carro from(CarroId carroId, List<DomainEvent> events){
        var carro = new Carro(carroId);
        events.forEach(carro::applyEvent);
        return carro;
    }


    public void asignarConductor(String nombre, Cedula cedula){
        appendChange(new ConductorAsignado(nombre, cedula)).apply();
    }


    public void cambiarKilometraje(CarrilId carrilId,Integer distancia){
        appendChange(new KilometrajeCambiado(distancia, carrilId)).apply();
    }


    public Conductor conductor() {
        return conductor;
    }

    public Integer distancia() {
        return distancia;
    }

    public String color() {
        return color.value();
    }


}
