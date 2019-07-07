package net.zinovev.services.bindings.dispatch.tack;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.Invoker;
import net.zinovev.services.bindings.dispatch.Message;
import net.zinovev.services.bindings.dispatch.AbstractSender;
import net.zinovev.services.bindings.kafka.RemoteMethodInfo;
import net.zinovev.services.bindings.test.server.SomeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class TackSender extends AbstractSender {
    @Autowired
    Invoker invoker;
    @Autowired
    ObjectMapper mapper;

    @Override
    public Message<?> messageReceived(Message<?> received) throws IOException, InvocationTargetException,
            IllegalAccessException, InterruptedException {
        log.debug("\n\n\n\nRequest received: {} by ", received, getClass().getSimpleName());
        try {
            sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        SomeBean someBean = (SomeBean) invoker.invoke((String) received.getPayload());
        Message<String> response = new Message<>();
        response.setPayload(mapper.writeValueAsString(someBean));
        send(response);
        log.debug("Response {} has been sent", response);
        return received;
    }
}
