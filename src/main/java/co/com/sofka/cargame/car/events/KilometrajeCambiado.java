package co.com.sofka.cargame.car.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class KilometrajeCambiado extends DomainEvent implements Incremental {
    private final Integer distancia;

    public Integer getDistancia() {
        return distancia;
    }

    public KilometrajeCambiado(Integer distancia) {
        super("cargame.car.KilometrajeCambiado");
        this.distancia = distancia;
    }
}
