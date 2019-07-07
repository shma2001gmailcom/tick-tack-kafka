package net.zinovev.services.bindings.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

@EnableBinding(Source.class)
@Slf4j
public abstract class AbstractSender implements Sender {
    @Autowired
    private MessageChannel output;

    @Override
    public void send(Message<?> m) throws InterruptedException, IllegalAccessException, IOException,
            InvocationTargetException {
        String jsonMessage;
        try {
            jsonMessage = new ObjectMapper().writeValueAsString(m);
            output.send(MessageBuilder.withPayload(jsonMessage).setHeader("messageType", m.getMessageType()).build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Observable o, Object received) {
        log.debug("update");
        try {
            Message<?> reply = messageReceived((Message<?>) received);
            send(reply);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public abstract Message<?> messageReceived(Message<?> received) throws IOException, InvocationTargetException,
            IllegalAccessException, InterruptedException;
}
