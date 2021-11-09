package co.com.sofka.cargame.infra.bus;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.bus.notification.ErrorNotification;
import co.com.sofka.infraestructure.bus.notification.SuccessNotification;
import co.com.sofka.infraestructure.bus.serialize.ErrorNotificationSerializer;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import co.com.sofka.infraestructure.event.ErrorEvent;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class NATSEventBus implements EventBus {
    private static final String ORIGIN = "cargame";
    private static final String TOPIC_ERROR = "cargame.error";
    private static final String TOPIC_BUSINESS_ERROR = "cargame.business.error";
    private static final Logger logger = Logger.getLogger(NATSEventBus.class.getName());

    private final Connection nc;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public NATSEventBus(@Value("${spring.nats.uri}") String natsUri, MongoTemplate mongoTemplate) throws IOException, InterruptedException {
        this.nc = Nats.connect(natsUri);
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = SuccessNotification.wrapEvent(ORIGIN, event);
        var notificationSerialization = SuccessNotificationSerializer.instance().serialize(notification);
        nc.publish(event.type + "." + event.aggregateRootId(), notificationSerialization.getBytes());
        mongoTemplate.save(event, event.type);
    }

    @Override
    public void publishError(ErrorEvent errorEvent) {

        if (errorEvent.error instanceof BusinessException) {
            publishToTopic(TOPIC_BUSINESS_ERROR, errorEvent);
        } else {
            publishToTopic(TOPIC_ERROR, errorEvent);
        }
        logger.log(Level.SEVERE, errorEvent.error.getMessage());

    }

    public void publishToTopic(String topic, ErrorEvent errorEvent) {
        var notification = ErrorNotification.wrapEvent(ORIGIN, errorEvent);
        var notificationSerialization = ErrorNotificationSerializer.instance().serialize(notification);
        nc.publish(topic + "." + errorEvent.identify, notificationSerialization.getBytes());
        logger.warning("###### Error Event published to " + topic);
    }

}