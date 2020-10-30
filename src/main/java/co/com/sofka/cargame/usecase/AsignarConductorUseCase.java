package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.cargame.aggregate.carro.Carro;
import co.com.sofka.cargame.aggregate.carro.command.AsignarConductorCommand;

public class AsignarConductorUseCase extends UseCase<RequestCommand<AsignarConductorCommand>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<AsignarConductorCommand> requestCommand) {
        var command = requestCommand.getCommand();

        var carro = Carro.from(command.getPlaca(), retrieveEvents());
        carro.asignarConductor(command.getNombre(), command.getCedula());

        emit().onSuccess(new ResponseEvents(carro.getUncommittedChanges()));
    }
}
