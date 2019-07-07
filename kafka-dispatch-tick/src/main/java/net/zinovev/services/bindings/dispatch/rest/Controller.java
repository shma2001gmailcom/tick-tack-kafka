package net.zinovev.services.bindings.dispatch.rest;

import antlr.collections.impl.IntRange;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.Message;
import net.zinovev.services.bindings.dispatch.tick.TickSender;
import net.zinovev.services.bindings.kafka.RemoteMethodInfo;
import net.zinovev.services.bindings.kafka.TypedValueInfo;
import net.zinovev.services.bindings.test.server.SomeBean;
import net.zinovev.services.bindings.test.server.TackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/")
public class Controller {
    @Autowired
    TackService tackService;
//    private final TickSender tickSender;

//    public Controller(TickSender tickSender) {this.tickSender = tickSender;}

    @RequestMapping("/start")
    public void start() throws InterruptedException {
        IntStream.range(0, 1).forEach(i -> {
            try {
                log.debug("Result={}", tackService.returnSomething(i, "s" + i));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
//        Message<String> m = new Message<>();
//        m.setCorrelationId(UUID.randomUUID().toString());
//        m.setMessageType("tick");
//        m.setPayload("{\"record\": \"hello from sender Tick\"}");
//        tickSender.send(m);
    }


}
