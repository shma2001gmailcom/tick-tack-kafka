package org.misha.dispatch.tack;

import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TickConfig {
    @Bean
    Sender sender() {
        return new TackSender();
    }

    @Bean
    Listener listener() {
        return new TickListener(sender());
    }
}
