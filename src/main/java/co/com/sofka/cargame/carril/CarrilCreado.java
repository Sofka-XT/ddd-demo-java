package co.com.sofka.cargame.carril;

import co.com.sofka.domain.generic.DomainEvent;

public class CarrilCreado extends DomainEvent {
    public CarrilCreado() {
        super("cargame.carril.CarrilCreado");
    }
}
