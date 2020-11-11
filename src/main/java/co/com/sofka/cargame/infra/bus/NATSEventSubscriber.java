package co.com.sofka.cargame.infra.bus;

import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class NATSEventSubscriber implements EventSubscriber {
    private static final Logger logger = Logger.getLogger(NATSEventSubscriber.class.getName());

    private final Dispatcher nats;

    public NATSEventSubscriber(String uri, EventListenerSubscriber eventSubscriber) throws IOException, InterruptedException {
        nats = Nats.connect(uri).createDispatcher(m -> {
            var message = new String(m.getData());
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            CompletableFuture.runAsync(() -> {
                try {
                    eventSubscriber.onNext(event);
                } catch (Exception e) {
                    eventSubscriber.onError(e);
                }
            });
        });
    }

    @Override
    public void subscribe(String eventType, String exchange) {
        nats.subscribe(eventType, exchange);
    }

}