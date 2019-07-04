package org.misha.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.Observer;

@EnableBinding(Source.class)
public abstract class Sender implements Observer {
    @Autowired
    private MessageChannel output;

    public void send(Message<?> m) throws InterruptedException {
        String jsonMessage;
        try {
            jsonMessage = new ObjectMapper().writeValueAsString(m);
            output.send(MessageBuilder.withPayload(jsonMessage).setHeader("messageType", m.getMessageType()).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

    public abstract Message<?> prepareReply(Message<?> received);
}
