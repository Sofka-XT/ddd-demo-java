package co.com.sofka.cargame.carril;

import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class Carril extends AggregateEvent<CarrilId> {
    protected CarroId carroId;

    public Carril(CarrilId entityId, Integer metros) {
        super(entityId);
    }

    public void aggregarCarro(CarroId carroId){

    }

    private Carril(CarrilId entityId){
        super(entityId);
    }

    public static Carril from(CarrilId entityId, List<DomainEvent> eventList){
        var carril = new Carril(entityId);
        eventList.forEach(carril::applyEvent);
        return carril;
    }

    public void moverCarro(Integer distancia) {

    }
}
