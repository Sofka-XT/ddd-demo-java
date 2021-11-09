package co.com.sofka.cargame.infra.bus;

import co.com.sofka.cargame.SocketController;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NATSEventSubscriber implements EventSubscriber {
    private static final Logger logger = Logger.getLogger(NATSEventSubscriber.class.getName());

    private final Dispatcher nats;

    public NATSEventSubscriber(String uri, EventListenerSubscriber eventSubscriber, SocketController socketController) throws IOException, InterruptedException {
        nats = Nats.connect(uri).createDispatcher(m -> {
            var message = new String(m.getData());
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            CompletableFuture.runAsync(() -> {
                try {
                    eventSubscriber.onNext(event);
                    Optional.ofNullable(event.aggregateParentId()).ifPresentOrElse(id -> {
                        socketController.send(id, event);
                    }, () -> socketController.send(event.aggregateRootId(), event));
                } catch (Exception e) {
                    eventSubscriber.onError(e);
                    logger.log(Level.SEVERE, e.getMessage());
                }
            });
        });
    }

    @Override
    public void subscribe(String eventType, String exchange) {
        nats.subscribe(eventType, exchange);
    }

}