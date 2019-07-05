package net.zinovev.services.bindings.dispatch.tick;


import net.zinovev.services.bindings.dispatch.Listener;
import net.zinovev.services.bindings.dispatch.Sender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TickConfig {

    @Bean
    Sender sender() {
        return new TickSender();
    }

    @Bean
    Listener listener() {
        return new TackListener(sender());
    }
}

