package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.cargame.domain.Color;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.command.CrearJuegoCommand;
import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.JugadorId;
import co.com.sofka.cargame.domain.juego.values.Nombre;
import co.com.sofka.cargame.domain.juego.values.Pista;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class CrearJuegoUseCase extends UseCase<RequestCommand<CrearJuegoCommand>, ResponseEvents> {
    private final Set<Integer> generados = new HashSet<>();
    private final Random random = new Random();
    private final Map<Integer, String> colores;

    public CrearJuegoUseCase() {
        this.colores = Map.of(
                1, "#000000",
                2, "#FFFFFF",
                3, "#FF0000",
                4, "#00FF00",
                5, "#0000FF",
                6, "#FFFF00",
                7, "#00FFFF",
                8, "#00FFFF",
                9, "#FF00FF",
                10, "#C2C2C2"
        );
    }

    @Override
    public void executeUseCase(RequestCommand<CrearJuegoCommand> requestCommand) {
        var command = requestCommand.getCommand();
        var juego = new Juego(new JuegoId(), new Pista(
                command.getKilometros(), command.getJugadores().size()
        ));

        command.getJugadores().forEach((key, value) ->
                juego.crearJugador(
                        JugadorId.of(key),
                        new Nombre(value),
                        new Color(generarColorAleatorio())
                )
        );
        emit().onSuccess(new ResponseEvents(juego.getUncommittedChanges()));
    }

    private String generarColorAleatorio() {
        boolean generado = false;
        int aleatorio = 0;
        while (!generado) {
            int posible = 1 + random.nextInt(9);
            if (!generados.contains(posible)) {
                generados.add(posible);
                aleatorio = posible;
                generado = true;
            }
        }
        if (aleatorio == 0) {
            generados.clear();
            return generarColorAleatorio();
        }

        return colores.get(aleatorio);
    }
}
