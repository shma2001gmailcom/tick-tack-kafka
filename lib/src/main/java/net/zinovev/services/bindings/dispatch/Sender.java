package net.zinovev.services.bindings.dispatch;

import java.util.Observer;

public interface Sender extends Observer {
    void send(Message<?> m) throws InterruptedException;
}
