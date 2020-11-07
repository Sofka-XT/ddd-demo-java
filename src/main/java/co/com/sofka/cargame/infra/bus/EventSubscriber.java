package co.com.sofka.cargame.infra.bus;

public interface EventSubscriber {
    void subscribe(String eventType, String exchange);
}