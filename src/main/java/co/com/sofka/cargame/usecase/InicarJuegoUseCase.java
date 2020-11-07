package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.command.InicarJuegoCommand;

public class InicarJuegoUseCase extends UseCase<RequestCommand<InicarJuegoCommand>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<InicarJuegoCommand> requestCommand) {
        var command = requestCommand.getCommand();
        var juego = Juego.from(command.getJuegoId(), retrieveEvents());
        juego.iniciarJuego();
        emit().onSuccess(new ResponseEvents(juego.getUncommittedChanges()));
    }
}
