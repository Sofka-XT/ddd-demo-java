package co.com.sofka.cargame.carril.events;

import co.com.sofka.cargame.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroAgregadoACarrail extends DomainEvent {
    private final CarroId carroId;

    public CarroAgregadoACarrail(CarroId carroId) {
        super("cargame.carril.CarroAgregadoACarrail");
        this.carroId = carroId;
    }

    public CarroId getCarroId() {
        return carroId;
    }

}
