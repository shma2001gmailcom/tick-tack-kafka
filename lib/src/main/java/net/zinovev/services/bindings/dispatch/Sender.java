package net.zinovev.services.bindings.dispatch;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observer;

public interface Sender extends Observer {
    void send(Message<?> m) throws InterruptedException, IllegalAccessException, IOException, InvocationTargetException;
}
