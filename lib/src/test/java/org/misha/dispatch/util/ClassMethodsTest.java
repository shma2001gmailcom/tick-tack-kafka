package org.misha.dispatch.util;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassMethodsTest {
    @Test
    public void describe() throws Exception {
        System.out.println(ClassMethods.describePublic(ClassPathXmlApplicationContext.class));
    }
}