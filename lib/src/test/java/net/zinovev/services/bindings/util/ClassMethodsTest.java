package net.zinovev.services.bindings.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class ClassMethodsTest {
    @Test
    public void describe() throws Exception {
        String msg = ClassMethods.describePublic(ClassPathXmlApplicationContext.class);
        log.debug(msg);
        assertNotNull(msg);
    }
}