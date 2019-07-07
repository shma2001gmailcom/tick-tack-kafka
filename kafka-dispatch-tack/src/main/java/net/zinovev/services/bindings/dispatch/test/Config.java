package net.zinovev.services.bindings.dispatch.test;

import net.zinovev.services.bindings.test.server.SomeBean;
import net.zinovev.services.bindings.test.server.impl.SomeBeanImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Configuration
public class Config {
    @Bean
    public net.zinovev.services.bindings.test.server.TackService tackService() {
        return new TackService(anotherService());
    }

    @Bean
    AnotherService anotherService() {
        return new AnotherService(someBeanFactory());
    }

    @Bean
    @Scope("prototype")
    net.zinovev.services.bindings.test.server.SomeBean someBean() {
        return new SomeBeanImpl();
    }

    @Component
    public static class TackService implements net.zinovev.services.bindings.test.server.TackService {
        private final AnotherService anotherService;

        public TackService(final AnotherService anotherService) {
            this.anotherService = anotherService;
        }

        /**
         * Used by a caller on another JVM as a return value
         *
         * @param i sample int parameter
         * @param s sample String parameter
         * @return SomeBean containing the parameters
         */
        @SuppressWarnings("unused")
        public net.zinovev.services.bindings.test.server.SomeBean returnSomething(Integer i, String s) {
            return anotherService.getSomeBean(i, s);
        }
    }

    @Component
    static class AnotherService {
        private final BiFunction<Integer, String, SomeBean> someBeanFactory;

        AnotherService(final BiFunction<Integer, String, SomeBean> someBeanFactory) {
            this.someBeanFactory = someBeanFactory;
        }

        net.zinovev.services.bindings.test.server.SomeBean getSomeBean(Integer i, String s) {
            return someBeanFactory.apply(i, s);
        }
    }

    /**
     * SomeBean should be a prototype
     * @return a Factory supplying a new SomeBean on each getSomeBean() call
     */
    @Bean
    BiFunction<Integer, String, SomeBean> someBeanFactory() {
        return SomeBeanImpl::new;
    }
}
