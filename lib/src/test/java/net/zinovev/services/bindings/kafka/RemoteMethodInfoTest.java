package net.zinovev.services.bindings.kafka;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static org.junit.Assert.assertEquals;

public class RemoteMethodInfoTest {
    private static final String CONFIG = "net.zinovev.services.bindings.dispatch.test.Config.";

    @Test
    public void test() throws IOException {
        RemoteMethodInfo expected
                = RemoteMethodInfo.builder()
                                  .methodName("returnSomething")
                                  .serviceName(CONFIG + "TackService")
                                  .args(Lists.newArrayList(new TypedValueInfo("java.lang.Integer", "1"),
                                                           new TypedValueInfo("java.lang.String", "s")
                                  )).returnType(CONFIG + "SomeBean").build();
        final RemoteMethodInfo actual = RemoteMethodInfo.fromJson(
                Resources.toString(getResource("method-info.json"), UTF_8));
        assertEquals(expected, actual);
    }
}