package org.misha.dispatch.tack;

import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Message;
import org.misha.dispatch.AbstractSender;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class TackSender extends AbstractSender {

    @Override
    public Message<?> makeReply(Message<?> received) {
        log.debug("***************************");
        try {
            sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Message<String> m = (Message) received;
        m.setMessageType("tack");
        m.setSender(getClass().getSimpleName());
        m.setPayload("{\"Record\":\"hello  from sender " + getClass().getSimpleName() + "\"}");
        return m;
    }
}
