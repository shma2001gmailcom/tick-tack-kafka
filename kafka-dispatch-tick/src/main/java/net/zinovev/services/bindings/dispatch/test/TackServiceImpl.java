package net.zinovev.services.bindings.dispatch.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.Message;
import net.zinovev.services.bindings.dispatch.tick.TackListener;
import net.zinovev.services.bindings.dispatch.tick.TickSender;
import net.zinovev.services.bindings.kafka.RemoteMethodInfo;
import net.zinovev.services.bindings.kafka.TypedValueInfo;
import net.zinovev.services.bindings.test.server.SomeBean;
import net.zinovev.services.bindings.test.server.TackService;
import net.zinovev.services.bindings.test.server.impl.SomeBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@Component
public class TackServiceImpl implements TackService {
    @Autowired
    TickSender tickSender;
    @Autowired
    TackListener tackListener;
    @Autowired
    ObjectMapper mapper;

    @Override
    public SomeBean returnSomething(final Integer i,
                                    final String s
    ) throws IOException, InterruptedException, ExecutionException, InvocationTargetException,
            IllegalAccessException, TimeoutException {
        RemoteMethodInfo remoteMethodInfo
                = RemoteMethodInfo.builder()
                                  .serviceName("net.zinovev.services.bindings.dispatch.test.Config.TackService")
                                  .methodName("returnSomething")
                                  .args(Lists.newArrayList(new TypedValueInfo("java.lang.Integer", "" + i),
                                                           new TypedValueInfo("java.lang.String", s))).build();
        String payload = mapper.writeValueAsString(remoteMethodInfo);
        final Message<String> request = new Message<>();
        request.setMessageType("request for SomeBean");
        request.setSender(getClass().getSimpleName());
        request.setPayload(payload);
        tickSender.send(request);
//        Message<?> response = tickSender.messageReceived(request);
//        checkArgument(response != null);
//        String responseJson = (String) response.getPayload();
//        log.debug("\n\n\n\nResponse received: {}", responseJson);
//        final SomeBeanImpl someBean = mapper.readValue(responseJson, SomeBeanImpl.class);
//        log.debug("SomeBean: {}: ", someBean);
        return null;
    }
}
