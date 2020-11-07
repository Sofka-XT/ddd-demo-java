package co.com.sofka.cargame.domain.carril.events;

import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;

public class CarroAgregadoACarrail extends DomainEvent implements Incremental {
    private final CarroId carroId;

    public CarroAgregadoACarrail(CarroId carroId) {
        super("carril.CarroAgregadoACarrail");
        this.carroId = carroId;
    }

    public CarroId getCarroId() {
        return carroId;
    }

}
