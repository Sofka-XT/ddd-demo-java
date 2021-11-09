package co.com.sofka.cargame.domain.carro.events;

import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class KilometrajeCambiado extends DomainEvent implements Incremental {
    private final Integer distancia;
    private final CarrilId carrilId;
    private final JuegoId juegoId;

    public KilometrajeCambiado(Integer distancia, CarrilId carrilId, JuegoId juegoId) {
        super("carro.KilometrajeCambiado");
        this.juegoId = juegoId;
        setAggregateParentId(this.juegoId.value());
        this.distancia = distancia;
        this.carrilId = carrilId;
    }

    public CarrilId getCarrilId() {
        return carrilId;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }
}
