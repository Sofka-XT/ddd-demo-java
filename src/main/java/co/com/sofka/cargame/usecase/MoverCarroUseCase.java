package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.cargame.domain.carro.Carro;
import co.com.sofka.cargame.domain.carro.command.MoverCarroCommand;

public class MoverCarroUseCase extends UseCase<RequestCommand<MoverCarroCommand>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<MoverCarroCommand> moverCarroCommandRequestCommand) {
        var command = moverCarroCommandRequestCommand.getCommand();
        var carro = Carro.from(command.getCarroId(), retrieveEvents());
        carro.avanzarEnCarril(command.getCarrilId());
        emit().onSuccess(new ResponseEvents(carro.getUncommittedChanges()));
    }
}
