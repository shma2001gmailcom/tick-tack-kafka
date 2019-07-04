package org.misha.dispatch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;
import java.util.Observable;

import static java.lang.Thread.sleep;

@EnableBinding(Sink.class)
public abstract class Listener extends Observable {

    @StreamListener(target = Sink.INPUT)
    public void listen(String messageJson) throws InterruptedException, IOException {
        setChanged();
        sleep(2000);
        Message<String> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<String>>() {});
        notifyObservers(message);
        clearChanged();
    }

     protected abstract Message<String> reply(String messageJson) throws IOException;
}
