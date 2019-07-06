package net.zinovev.services.bindings.dispatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.zinovev.services.bindings.kafka.RemoteMethodInfo;
import net.zinovev.services.bindings.kafka.TypedValueInfo;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.ClassUtils.forName;
import static org.springframework.util.ReflectionUtils.findMethod;

@Component
public class Invoker {
    private final ListableBeanFactory singletons;
    private final BeanFactory beanFactory;

    @Autowired
    public Invoker(final ListableBeanFactory singletons, final BeanFactory beanFactory) {
        this.singletons = singletons;
        this.beanFactory = beanFactory;
    }

    public Object invoke(@NotNull String methodInfoJson)
            throws InvocationTargetException, IllegalAccessException, IOException {
        RemoteMethodInfo methodInfo = new ObjectMapper().readValue(methodInfoJson, RemoteMethodInfo.class);
        Class<?> c = findClass(methodInfo.getServiceName());
        Method m = findMethod(c, methodInfo.getMethodName(), methodInfo.getArgs().stream()
                                                                       .map(TypedValueInfo::resolveType)
                                                                       .collect(toList()).toArray(new Class<?>[]{}));
        checkArgument(m != null);
        return m.invoke(findBeanByClass(c), methodInfo.getArgs().stream()
                                                      .map(TypedValueInfo::resolve)
                                                      .toArray());
    }

    private Object findBeanByClass(final Class<?> c) {
        return singletons.getBeansOfType(c).entrySet().stream()
                                         .filter(b -> beanFactory.isSingleton(b.getKey()))
                                         .filter(b -> b.getValue().getClass().isAssignableFrom(c))
                                         .findFirst()
                                         .orElseThrow(() -> new IllegalStateException("Missing Singleton of type " + c))
                                         .getValue();
    }

    private static Class<?> findClass(String name) {
        try {
            return forName(name, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            return ClassNotFoundException.class;
        }
    }
}
