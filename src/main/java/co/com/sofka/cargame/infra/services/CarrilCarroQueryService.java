package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.model.CarroSobreCarril;
import co.com.sofka.cargame.usecase.services.CarrilCarroService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CarrilCarroQueryService implements CarrilCarroService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CarrilCarroQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<CarroSobreCarril> getCarrosSobreCarriles(JuegoId juegoId) {
        var lookup = LookupOperation.newLookup()
                .from("carril.CarroAgregadoACarrail")
                .localField("aggregateRootId")
                .foreignField("aggregateRootId")
                .as("carroAgregadoACarrail");

        var aggregation = Aggregation.newAggregation(
                lookup,
                Aggregation.match(where("juegoId.uuid").is(juegoId.value()))
        );

        return mongoTemplate.aggregate(aggregation, "carril.CarrilCreado", String.class)
                .getMappedResults().stream()
                .map(body -> new Gson().fromJson(body, CarroSobreCarrilRecord.class))
                .map(carroSobreCarrilRecord -> {
                    var carroCarril = new CarroSobreCarril();
                    carroCarril.setCarrilId(carroSobreCarrilRecord.aggregateRootId);
                    carroCarril.setCarroId(carroSobreCarrilRecord.getCarroAgregadoACarrail().get(0).getCarroId().getUuid());
                    return carroCarril;
                }).collect(Collectors.toList());
    }

    public static class CarroSobreCarrilRecord {
        private String aggregateRootId;
        private List<CarroAgregadoACarrail> carroAgregadoACarrail;

        public String getAggregateRootId() {
            return aggregateRootId;
        }

        public void setAggregateRootId(String aggregateRootId) {
            this.aggregateRootId = aggregateRootId;
        }

        public List<CarroAgregadoACarrail> getCarroAgregadoACarrail() {
            return carroAgregadoACarrail;
        }

        public void setCarroAgregadoACarrail(List<CarroAgregadoACarrail> carroAgregadoACarrail) {
            this.carroAgregadoACarrail = carroAgregadoACarrail;
        }

        public static class CarroAgregadoACarrail {
            private CarroId carroId;

            public CarroId getCarroId() {
                return carroId;
            }

            public void setCarroId(CarroId carroId) {
                this.carroId = carroId;
            }

            public static class CarroId {
                private String uuid;

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }
            }
        }


    }
}
