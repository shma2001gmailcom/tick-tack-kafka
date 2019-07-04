package org.misha.dispatch.tick;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Component
@EnableBinding(Source.class)
@Slf4j
public class TickSender implements Observer {
    private final MessageChannel output;
    private final AtomicInteger count;

    @Autowired
    public TickSender(@Qualifier("output") MessageChannel output) {
        this.output = output;
        count = new AtomicInteger(0);
    }

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
        log.debug("\n\n\n\n update");
        Message<String> m = new Message<>();
        m.setCorrelationId(UUID.randomUUID().toString());
        m.setMessageType("tick");
        m.setSender(getClass().getSimpleName());
        m.setPayload("{\"Record\":\"hello #" + count.getAndIncrement() + " from sender Tick\"}");
        try {
            sleep(2000);
            log.debug("\n\n\n\n sending...");
            send(m);
            log.debug("\n\n\n------------------\n {} sent by {}", m, this.getClass().getSimpleName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
