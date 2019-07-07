package net.zinovev.services.bindings.dispatch.test;

import net.zinovev.services.bindings.test.server.TackService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    TackService tackService() {
        return new TackServiceImpl();
    }
}
