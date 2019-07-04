package org.misha.dispatch;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;
import java.util.Observable;

@EnableBinding(Sink.class)
public abstract class Listener extends Observable {

    @StreamListener(target = Sink.INPUT)
    public void onMessage(String messageJson) throws IOException {
        setChanged();
        Message<String> message = reply(messageJson);
        notifyObservers(message);
        clearChanged();
    }

    protected abstract Message<String> reply(String messageJson) throws IOException;
}
