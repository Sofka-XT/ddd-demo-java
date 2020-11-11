package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.usecase.services.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class JuegoQueryService implements JuegoService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public JuegoQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Integer getKilometros(JuegoId juegoId) {
        var query = new Query(where("aggregateRootId").is(juegoId.value()));
        return Objects.requireNonNull(mongoTemplate.findOne(query, JuegoRecord.class, "juego.JuegoCreado"))
                .getPista().value().kilometros();
    }

    public static class JuegoRecord {
        private Pista pista;

        public Pista getPista() {
            return pista;
        }

        public void setPista(Pista pista) {
            this.pista = pista;
        }
    }

}
