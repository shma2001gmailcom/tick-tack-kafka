package net.zinovev.services.bindings.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;
import net.zinovev.services.bindings.kafka.RemoteMethodInfo;
import net.zinovev.services.bindings.kafka.TypedValueInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.collect.Maps.newIdentityHashMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.util.ReflectionUtils.getAllDeclaredMethods;

@UtilityClass
public class ClassMethods {
    private static final Set<Method> skip = Sets.newHashSet(Object.class.getMethods());

    private static Set<Method> methods(Class<?> c) {
        return Arrays.stream(getAllDeclaredMethods(c))
                     .filter(m -> !skip.contains(m))
                     .collect(toSet());
    }

    public static String describePublic(final Class<?> c) throws JsonProcessingException {
        final Map<String, Set<RemoteMethodInfo>> result = newIdentityHashMap();
        result.put(c.getName(),
                   methods(c).stream()
                             .map(m -> describe().apply(m))
                             .filter(desc -> desc.getModifiers().contains("public") && !desc.getMethodName()
                                                                                            .contains("lambda"))
                             .collect(toSet()));
        return new ObjectMapper().writeValueAsString(result);
    }

    private static Function<Method, RemoteMethodInfo> describe() {
        return m -> RemoteMethodInfo.builder()
                                    .args(Arrays.stream(m.getParameterTypes())
                                                .map(c -> new TypedValueInfo(c.getName(), EMPTY))
                                                .collect(toList()))
                                    .methodName(m.getName())
                                    .annotations(Arrays.stream(m.getAnnotations())
                                                       .map(Annotation::toString)
                                                       .collect(toList()))
                                    .returnType(m.getReturnType().getName())
                                    .modifiers(Modifier.toString(m.getModifiers()))
                                    .build();
    }
}
