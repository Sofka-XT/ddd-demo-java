package co.com.sofka.cargame.carro.events;

import co.com.sofka.cargame.carril.values.CarrilId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class KilometrajeCambiado extends DomainEvent implements Incremental {
    private final Integer distancia;
    private final CarrilId carrilId;

    public CarrilId getCarrilId() {
        return carrilId;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public KilometrajeCambiado(Integer distancia, CarrilId carrilId) {
        super("cargame.car.KilometrajeCambiado");
        this.distancia = distancia;
        this.carrilId = carrilId;
    }
}
