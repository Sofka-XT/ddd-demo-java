package co.com.sofka.cargame.carril.events;

import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroFinalizoSuRecorrido extends DomainEvent {
    private final CarroId carroId;

    public CarroId getCarroId() {
        return carroId;
    }

    public CarroFinalizoSuRecorrido(CarroId carroId) {
        super("cargame.carril.CarroFinalizoSuRecorrido");
        this.carroId = carroId;
    }
}
