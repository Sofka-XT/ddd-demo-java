package co.com.sofka.cargame.carril.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CarrilCreado extends DomainEvent {

    private final Integer metros;

    public Integer getMetros() {
        return metros;
    }

    public CarrilCreado(Integer metros) {
        super("cargame.carril.CarrilCreado");
        this.metros = metros;
    }
}
