package net.zinovev.services.bindings.test.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface TackService {
    SomeBean returnSomething(Integer i, String s) throws IOException, InterruptedException, ExecutionException, InvocationTargetException, IllegalAccessException, TimeoutException;
}
