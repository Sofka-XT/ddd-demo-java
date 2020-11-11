package co.com.sofka.cargame.usecase.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.model.CarroSobreCarril;

import java.util.List;

public interface CarrilCarroService {
    List<CarroSobreCarril> getCarrosSobreCarriles(JuegoId juegoId);
}