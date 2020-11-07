package co.com.sofka.cargame.usecase.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;

public interface JuegoService {
    Integer getKilometros(JuegoId juegoId);
}