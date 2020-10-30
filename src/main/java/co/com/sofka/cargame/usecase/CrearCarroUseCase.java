package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.cargame.aggregate.carro.Carro;
import co.com.sofka.cargame.aggregate.carro.command.CrearCarroCommand;

public class CrearCarroUseCase extends UseCase<RequestCommand<CrearCarroCommand>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearCarroCommand> requestCommand) {
        var command = requestCommand.getCommand();
        var carro = new Carro(command.getPlaca(), command.getColor());
        emit().onSuccess(new ResponseEvents(carro.getUncommittedChanges()));
    }
}
