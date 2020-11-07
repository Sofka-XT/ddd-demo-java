package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.domain.carro.values.Cedula;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.usecase.model.CarroSobreCarril;
import co.com.sofka.cargame.usecase.services.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CarroQueryService implements CarroService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CarroQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String getConductorIdPor(CarroId carroId) {
        var query = new Query(where("aggregateRootId").is(carroId.value()));
        return  Objects.requireNonNull(mongoTemplate.findOne(query, CarroRecord.class, "carro.ConductorAsignado"))
                .getCedula().getUuid();
    }

    public static class CarroRecord  {
        private  String nombre;
        private  Cedula cedula;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Cedula getCedula() {
            return cedula;
        }

        public void setCedula(Cedula cedula) {
            this.cedula = cedula;
        }
    }
    public static class Cedula {
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
