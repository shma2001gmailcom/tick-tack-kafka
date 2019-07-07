package net.zinovev.services.bindings.test.server.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SomeBeanImpl implements net.zinovev.services.bindings.test.server.SomeBean {
    private final Integer i;
    private final String s;

    public SomeBeanImpl() {
        i = 0;
        s = "";
    }

    public SomeBeanImpl(final Integer i, final String s) {
        this.i = i;
        this.s = s;
    }

    @Override
    public Integer getI() {
        return i;
    }

    @Override
    public String getS() {
        return s;
    }

    @Override
    public String toString() {
        return "SomeBeanImpl{" +
                "i=" + i +
                ", s='" + s + '\'' +
                '}';
    }
}
