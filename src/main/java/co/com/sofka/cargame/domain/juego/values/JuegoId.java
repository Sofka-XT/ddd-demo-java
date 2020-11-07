package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.domain.generic.Identity;

public class JuegoId extends Identity {
    private JuegoId(String uuid) {
        super(uuid);
    }

    public JuegoId() {}
    public static JuegoId of(String uuid) {
        return new JuegoId(uuid);
    }
}
