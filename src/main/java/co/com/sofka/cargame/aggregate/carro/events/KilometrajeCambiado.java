package co.com.sofka.cargame.aggregate.carro.events;

import co.com.sofka.cargame.aggregate.carril.values.CarrilId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class KilometrajeCambiado extends DomainEvent implements Incremental {
    private final Integer distancia;
    private final CarrilId carrilId;

    public KilometrajeCambiado(Integer distancia, CarrilId carrilId) {
        super("cargame.car.KilometrajeCambiado");
        this.distancia = distancia;
        this.carrilId = carrilId;
    }

    public CarrilId getCarrilId() {
        return carrilId;
    }

    public Integer getDistancia() {
        return distancia;
    }
}
