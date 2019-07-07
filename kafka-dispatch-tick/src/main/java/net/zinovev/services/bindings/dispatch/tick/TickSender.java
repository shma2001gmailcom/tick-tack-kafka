package net.zinovev.services.bindings.dispatch.tick;

import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.Message;
import net.zinovev.services.bindings.dispatch.AbstractSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class TickSender extends AbstractSender {
    /**
     *
     * @param received
     * @return
     */
    @Override
    public Message<?> messageReceived(Message<?> received) {
        log.debug("\n\n\n\n update");
        log.error("\n\n\n\nReceived " + received);
        log.debug("\n\n\n\n");
        Message<String> m = new Message<>();
        m.setCorrelationId(UUID.randomUUID().toString());
        m.setMessageType("tick");
        m.setSender(getClass().getSimpleName());
        try {
            sleep(2000);
            log.debug("\n\n\n\n sending...");
            log.debug("\n\n\n------------------\n {} sent by {}", m, getClass().getSimpleName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return m;
    }
}
