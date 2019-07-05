package net.zinovev.services.bindings.dispatch.tack;

import net.zinovev.services.bindings.dispatch.Listener;
import net.zinovev.services.bindings.dispatch.Sender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TackConfig {

    @Bean
    Sender sender() {
        return new TackSender();
    }

    @Bean
    Listener listener() {
        return new TickListener(sender());
    }
}
