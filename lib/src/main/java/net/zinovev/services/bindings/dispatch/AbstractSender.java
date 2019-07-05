package net.zinovev.services.bindings.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.Observable;

@EnableBinding(Source.class)
@Slf4j
public abstract class AbstractSender implements Sender {
    @Autowired
    private MessageChannel output;

    @Override
    public void send(Message<?> m) throws InterruptedException {
        String jsonMessage;
        try {
            jsonMessage = new ObjectMapper().writeValueAsString(m);
            output.send(MessageBuilder.withPayload(jsonMessage).setHeader("messageType", m.getMessageType()).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        log.debug("update");
        Message<String> m = (Message<String>) makeReply((Message<String>) arg);
        try {
            send(m);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract Message<?> makeReply(Message<?> received);
}
