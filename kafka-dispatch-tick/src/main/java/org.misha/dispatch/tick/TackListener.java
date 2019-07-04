package org.misha.dispatch.tick;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Message;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.io.IOException;
import java.util.Observable;

import static java.lang.Thread.sleep;

@EnableBinding(Sink.class)
@Slf4j
class TackListener extends Observable {
    TackListener(TickSender tickSender) {
        addObserver(tickSender);
    }

    @StreamListener(target = Sink.INPUT)
    void listen(String messageJson) throws IOException, InterruptedException {
        log.debug("\n\n\n+++++\n listen");
        setChanged();
        sleep(2000);
        Message<String> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<String>>() {});
        log.debug("\n\n\n------------------\n {} received by {}", message, this.getClass().getSimpleName());
        notifyObservers(message);
        clearChanged();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
        log.debug("\n\n\n\n notified");
    }
}
