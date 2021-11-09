package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.cargame.domain.juego.Jugador;
import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Podio implements ValueObject<Podio.Props> {
    private final Jugador primerLugar;
    private final Jugador segundoLugar;
    private final Jugador tercerLugar;

    private Podio(Jugador primerLugar, Jugador segundoLugar, Jugador tercerLugar) {
        this.primerLugar = primerLugar;
        this.segundoLugar = segundoLugar;
        this.tercerLugar = tercerLugar;
    }

    public Podio() {
        primerLugar = null;
        segundoLugar = null;
        tercerLugar = null;
    }


    public Podio asignarPrimerLugar(Jugador jugador) {
        return new Podio(jugador, segundoLugar, tercerLugar);
    }

    public Podio asignarSegundoLugar(Jugador jugador) {
        return new Podio(primerLugar, jugador, tercerLugar);
    }

    public Podio asignarTercerLugar(Jugador jugador) {
        return new Podio(primerLugar, segundoLugar, jugador);
    }

    @Override
    public Props value() {
        return new Props() {
            @Override
            public Jugador primerLugar() {
                return primerLugar;
            }

            @Override
            public Jugador segundoLugar() {
                return segundoLugar;
            }

            @Override
            public Jugador tercerLugar() {
                return tercerLugar;
            }

            @Override
            public Boolean estaLLeno() {
                return Objects.nonNull(primerLugar) && Objects.nonNull(segundoLugar) && Objects.nonNull(tercerLugar);
            }
        };
    }

    public interface Props {
        Jugador primerLugar();

        Jugador segundoLugar();

        Jugador tercerLugar();

        Boolean estaLLeno();
    }
}
