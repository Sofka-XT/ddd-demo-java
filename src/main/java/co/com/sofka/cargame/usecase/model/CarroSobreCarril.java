package co.com.sofka.cargame.usecase.model;

public class CarroSobreCarril {
    private String carroId;
    private String carrilId;

    public CarroSobreCarril() {
    }

    public CarroSobreCarril(String carroId, String carrilId) {
        this.carroId = carroId;
        this.carrilId = carrilId;
    }

    public String getCarroId() {
        return carroId;
    }

    public void setCarroId(String carroId) {
        this.carroId = carroId;
    }

    public String getCarrilId() {
        return carrilId;
    }

    public void setCarrilId(String carrilId) {
        this.carrilId = carrilId;
    }

    @Override
    public String toString() {
        return "CarroSobreCarril{" +
                "carroId='" + carroId + '\'' +
                ", carrilId='" + carrilId + '\'' +
                '}';
    }
}