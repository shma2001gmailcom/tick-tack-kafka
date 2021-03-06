package net.zinovev.services.bindings.dispatch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;
import java.util.Observable;

import static java.lang.Thread.sleep;

@EnableBinding(Sink.class)
@Slf4j
public abstract class AbstractListener extends Observable implements Listener {
    @Override
    @StreamListener(target = Sink.INPUT)
    public void onMessage(String messageJson) throws IOException {
        setChanged();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Message<?> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<String>>() {});
        log.debug("Message: {} ", message);
        if (hasChanged()) {
            notifyObservers(message);
        }
        clearChanged();
    }
}
