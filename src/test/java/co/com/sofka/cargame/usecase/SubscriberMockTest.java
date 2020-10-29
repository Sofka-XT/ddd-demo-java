package co.com.sofka.cargame.usecase;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.concurrent.Flow;

public class SubscriberMockTest implements Flow.Subscriber<DomainEvent> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(DomainEvent event) {
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}