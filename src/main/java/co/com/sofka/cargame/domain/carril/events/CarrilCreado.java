package co.com.sofka.cargame.domain.carril.events;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarrilCreado extends DomainEvent {

    private final Integer metros;
    private final JuegoId juegoId;

    public CarrilCreado(Integer metros, JuegoId juegoId) {
        super("carril.CarrilCreado");
        setAggregateParentId(juegoId.value());
        this.metros = metros;
        this.juegoId = juegoId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public Integer getMetros() {
        return metros;
    }
}
