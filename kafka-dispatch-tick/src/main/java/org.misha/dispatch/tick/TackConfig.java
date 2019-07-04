package org.misha.dispatch.tick;

import org.misha.dispatch.Listener;
import org.misha.dispatch.Sender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TackConfig {
    @Bean
    Sender sender() {
        return new TickSender();
    }

    @Bean
    Listener listener() {
        return new TackListener(sender());
    }
}

