package net.zinovev.services.bindings.kafka;

import net.zinovev.services.bindings.core.ServiceBinder;
import net.zinovev.services.bindings.exceptions.NotImplementedException;

import javax.validation.constraints.NotNull;

public class KafkaServiceBinder implements ServiceBinder {

    @Override
    public <T> @NotNull T bind(@NotNull Class<T> service, @NotNull T serviceImpl) {
        throw new NotImplementedException();
    }

    @Override
    public <T> @NotNull T bindClient(@NotNull Class<? extends T> service) {
        throw new NotImplementedException();
    }
}