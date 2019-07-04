package org.misha.rest;

import org.misha.dispatch.Message;
import org.misha.dispatch.tick.TickSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class Controller {
    private final TickSender tickSender;

    public Controller(TickSender tickSender) {this.tickSender = tickSender;}

    @RequestMapping("/start")
    public void start() throws InterruptedException {
        Message<String> m = new Message<>();
        m.setCorrelationId(UUID.randomUUID().toString());
        m.setMessageType("tick");
        m.setPayload("{\"record\": \"hello from sender Tick\"}");
        tickSender.send(m);
    }
}
