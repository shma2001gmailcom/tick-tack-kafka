package net.zinovev.services.bindings.dispatch.test;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import net.zinovev.services.bindings.dispatch.Invoker;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;

@Slf4j
@Component
public class InvokerTest {
    private final Invoker invoker;

    public InvokerTest(final Invoker invoker) {
        this.invoker = invoker;
    }

    //@PostConstruct
    public void invoke() throws Exception {
        log.error(invoker.invoke(Resources.toString(getResource("test/method-info.json"), UTF_8))
                         .toString());
    }
}