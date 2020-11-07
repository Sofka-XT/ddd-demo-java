package co.com.sofka.cargame.infra.repo;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MongoEventStoreRepository implements EventStoreRepository {
    private final MongoTemplate mongoTemplate;

    public MongoEventStoreRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<DomainEvent> getEventsBy(String  aggregateName, String aggregateRootId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateRootId));
        var find = mongoTemplate.find(query, DocumentEventStored.class, aggregateName);
        return find.stream()
                .map(aggregate -> aggregate.getStoredEvent().deserializeEvent())
                .sorted(Comparator.comparing(event -> event.when))
                .collect(Collectors.toList());

    }

    @Override
    public void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent) {
        var eventStored = new DocumentEventStored();
        eventStored.setAggregateRootId(aggregateRootId);
        eventStored.setStoredEvent(storedEvent);
        mongoTemplate.save(eventStored,aggregateName);
    }
}
