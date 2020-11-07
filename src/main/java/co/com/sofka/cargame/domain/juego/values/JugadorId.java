package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.domain.generic.Identity;

public class JugadorId extends Identity {
    private JugadorId(String key) {
        super(key);
    }

    public static JugadorId of(String key){
        return new JugadorId(key);
    }
}
