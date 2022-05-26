package co.com.sofka.cargame.domain.juego.events;

import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.domain.generic.DomainEvent;

public class CompetidorIniciado extends DomainEvent {
    private final CarroId carroId;
    private final CarrilId carrilId;

    public CompetidorIniciado(CarroId carroId, CarrilId carrilId) {
        super("juego.CompetidorIniciado");
        this.carroId = carroId;
        this.carrilId = carrilId;
    }

    public CarrilId getCarrilId() {
        return carrilId;
    }

    public CarroId getCarroId() {
        return carroId;
    }
}
