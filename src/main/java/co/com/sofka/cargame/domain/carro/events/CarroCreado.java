package co.com.sofka.cargame.domain.carro.events;

import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.domain.generic.DomainEvent;

public class CarroCreado extends DomainEvent {
    private final CarroId carroId;
    private final Color color;
    private final JuegoId juegoId;


    public CarroCreado(CarroId carroId, JuegoId juegoId, Color color) {
        super("carro.CarroCreado");
        setAggregateParentId(juegoId.value());
        this.carroId = carroId;
        this.color = color;
        this.juegoId = juegoId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public CarroId getPlaca() {
        return carroId;
    }

    public Color getColor() {
        return color;
    }
}
