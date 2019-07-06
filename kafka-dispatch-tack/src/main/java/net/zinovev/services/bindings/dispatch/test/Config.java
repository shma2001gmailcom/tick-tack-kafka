package net.zinovev.services.bindings.dispatch.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
public class Config {
    @Bean
    public TackService tackService() {
        return new TackService(anotherService());
    }

    @Bean
    AnotherService anotherService() {
        return new AnotherService(someBeanHolder());
    }

    @Bean
    @Scope("prototype")
    SomeBean someBean() {
        return new SomeBean();
    }

    @Bean
    SomeBeanHolder someBeanHolder() {
        return new SomeBeanHolder();
    }

    @Component
    public static class TackService {
        private final AnotherService anotherService;

        public TackService(final AnotherService anotherService) {
            this.anotherService = anotherService;
        }

        public SomeBean returnSomething(Integer i, String s) {
            return anotherService.getSomeBean(i, s);
        }
    }

    @Component
    static class AnotherService {
        private final SomeBeanHolder someBeanHolder;

        AnotherService(final SomeBeanHolder someBeanHolder) {
            this.someBeanHolder = someBeanHolder;
        }

        SomeBean getSomeBean(Integer i, String s) {
            return someBeanHolder.newSomeBean(i, s);
        }
    }

    @Component
    public static class SomeBean {
        private final Integer i;
        private final String s;

        public SomeBean() {
            i = 0;
            s = "";
        }

        public SomeBean(final Integer i, final String s) {
            this.i = i;
            this.s = s;
        }

        public Integer getI() {
            return i;
        }

        public String getS() {
            return s;
        }

        @Override
        public String toString() {
            return "SomeBean{" +
                    "i=" + i +
                    ", s='" + s + '\'' +
                    '}';
        }
    }

    @Component
    static class SomeBeanHolder {
        SomeBean newSomeBean(Integer i, String s) {
            return new SomeBean(i, s);
        }
    }
}
