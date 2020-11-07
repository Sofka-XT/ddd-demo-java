package co.com.sofka.cargame.infra.bus;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NATSEventSubscriber implements EventSubscriber {
    private static final  Logger logger = Logger.getLogger(NATSEventSubscriber.class.getName());

    private final Dispatcher nats;
    private final SubmissionPublisher<DomainEvent> publisher = new SubmissionPublisher<>();

    public NATSEventSubscriber(String uri, Flow.Subscriber<DomainEvent> eventSubscriber) throws IOException, InterruptedException {
        publisher.subscribe(eventSubscriber);
         nats = Nats.connect(uri).createDispatcher(m -> {
            var message = new String(m.getData());
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            logger.info("##################################################################");
            logger.info("###### Recibe message form " + event.type + " -- " + event.getClass().getName());

                CompletableFuture.runAsync(() -> {
                    try {
                        publisher.submit(event);
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, e.getMessage(), e);
                    }
                });

             logger.info("##################################################################");
        });
    }

    @Override
    public void subscribe(String eventType, String exchange){
        nats.subscribe(eventType, exchange);
    }

}