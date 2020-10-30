package co.com.sofka.cargame.carril;

import co.com.sofka.cargame.carril.events.CarrilCreado;
import co.com.sofka.cargame.carril.events.CarroAgregadoACarrail;
import co.com.sofka.cargame.carril.events.CarroDesplazado;
import co.com.sofka.cargame.carril.events.CarroFinalizoSuRecorrido;
import co.com.sofka.cargame.carril.values.CarrilId;
import co.com.sofka.cargame.carril.values.Posicion;
import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class Carril extends AggregateEvent<CarrilId> {
    protected CarroId carroId;
    protected Posicion postion;
    protected Integer metros;
    protected Boolean desplazamientoFinal;

    public Carril(CarrilId entityId, Integer metros) {
        super(entityId);
        appendChange(new CarrilCreado(metros)).apply();
    }

    public void aggregarCarro(CarroId carroId){
        appendChange(new CarroAgregadoACarrail(carroId)).apply();
    }

    public void alcazarLaMeta(){
        appendChange(new CarroFinalizoSuRecorrido(carroId)).apply();
    }

    public void moverCarro(Integer distancia) {
        appendChange(new CarroDesplazado(new Posicion(distancia, metros()))).apply();
    }

    private Carril(CarrilId entityId){
        super(entityId);
        subscribe(new CarrilEventChange(this));
    }

    public static Carril from(CarrilId entityId, List<DomainEvent> eventList){
        var carril = new Carril(entityId);
        eventList.forEach(carril::applyEvent);
        return carril;
    }



    public Integer metros() {
        return metros;
    }

    public Integer posicionActual(){
        return postion.value().actual();
    }

    public Integer posicionDeseada(){
        return postion.value().metal();
    }

    public Boolean desplazamientoFinal(){
        return desplazamientoFinal;
    }
}
