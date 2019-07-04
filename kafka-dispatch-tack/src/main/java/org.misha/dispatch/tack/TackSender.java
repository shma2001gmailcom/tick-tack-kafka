package org.misha.dispatch.tack;

import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Message;
import org.misha.dispatch.Sender;
import org.springframework.stereotype.Component;

import java.util.Observable;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class TackSender extends Sender {
    @Override
    public void update(Observable o, Object arg) {
        Message<String> m = (Message<String>) prepareReply((Message<String>) arg);
        try {
            sleep(2000);
            log.debug("\n\n\n\n sending...");
            send(m);
            log.debug("\n\n\n------------------\n {} sent by {}", m, getClass().getSimpleName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Message<?> prepareReply(Message<?> received) {
        Message<String> m = (Message) received;
        m.setMessageType("tack");
        m.setSender(getClass().getSimpleName());
        m.setPayload("{\"Record\":\"hello  from sender " + getClass().getSimpleName() + "\"}");
        return m;
    }
}
