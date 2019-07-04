package net.zinovev.services.bindings.kafka;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClientKafkaInvocationHandler<T> implements InvocationHandler {
    private Class<T> service;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        T impl = service.newInstance();
        return method.invoke(impl, args);
    }
}
