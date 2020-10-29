package co.com.sofka.cargame.usecase;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UseCaseHandleBaseTest {
    @Captor
    protected ArgumentCaptor<DomainEvent> eventCaptor;

    @Captor
    protected ArgumentCaptor<BusinessException> eventCaptorError;

    @Mock
    protected DomainEventRepository repository;

    @Spy
    protected SubscriberMockTest subscriber;
}