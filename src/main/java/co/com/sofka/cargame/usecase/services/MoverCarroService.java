package co.com.sofka.cargame.usecase.services;

import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.values.CarroId;

public interface MoverCarroService {
    void mover(CarroId carroId, CarrilId carrilId);
}
