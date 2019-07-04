package net.zinovev.services.bindings.core;

import javax.validation.constraints.NotNull;

public interface ServiceBinder {

    @NotNull <T> T bind(@NotNull Class<T> service, @NotNull T serviceImpl);

    @NotNull <T> T bindClient(@NotNull Class<? extends T> service);
}

