package co.com.sofka.cargame.domain.carro;

import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.events.CarroCreado;
import co.com.sofka.cargame.domain.carro.events.ConductorAsignado;
import co.com.sofka.cargame.domain.carro.events.KilometrajeCambiado;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class Carro extends AggregateEvent<CarroId> {

    protected Conductor conductor;
    protected Integer distancia;
    protected Color color;
    protected JuegoId juegoId;

    public Carro(CarroId carroId, JuegoId juegoId, Color color) {
        super(carroId);
        appendChange(new CarroCreado(carroId, juegoId, color)).apply();
    }

    private Carro(CarroId carroId) {
        super(carroId);
        subscribe(new CarroChange(this));
    }

    public static Carro from(CarroId carroId, List<DomainEvent> events) {
        var carro = new Carro(carroId);
        events.forEach(carro::applyEvent);
        return carro;
    }


    public void asignarConductor(String nombre, Cedula cedula) {
        appendChange(new ConductorAsignado(nombre, cedula)).apply();
    }


    public void avanzarEnCarril(CarrilId carrilId) {
        var avance = conductor.lanzarDado() * 100;
        appendChange(new KilometrajeCambiado(avance, carrilId, juegoId)).apply();
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
