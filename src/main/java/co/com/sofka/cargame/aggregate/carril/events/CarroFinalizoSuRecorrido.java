package co.com.sofka.cargame.aggregate.carril.events;

import co.com.sofka.cargame.aggregate.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroFinalizoSuRecorrido extends DomainEvent {
    private final CarroId carroId;

    public CarroFinalizoSuRecorrido(CarroId carroId) {
        super("cargame.carril.CarroFinalizoSuRecorrido");
        this.carroId = carroId;
    }

    public CarroId getCarroId() {
        return carroId;
    }
}
