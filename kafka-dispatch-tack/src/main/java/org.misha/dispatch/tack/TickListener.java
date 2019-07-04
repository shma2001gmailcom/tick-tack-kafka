package org.misha.dispatch.tack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.misha.dispatch.Listener;
import org.misha.dispatch.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static java.lang.Thread.sleep;

@Slf4j
@Component
class TickListener extends Listener {
    @Autowired
    private TackSender tackSender;

    @Override
    protected Message<String> reply(String messageJson) throws IOException {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message<String> message = new ObjectMapper().readValue(messageJson, new TypeReference<Message<String>>() {});
        log.debug("\n\n\n------------------\n {} received by {}", message, this.getClass().getSimpleName());
        return message;
    }


    @PostConstruct
    private void addSender() {
        addObserver(tackSender);
    }
}
