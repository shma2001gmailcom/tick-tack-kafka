package net.zinovev.services.bindings.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class RemoteMethodInfo implements Serializable {
    private static final long serialVersionUID = 6310352552947174225L;
    @NotNull
    private String serviceName;
    @NotNull
    private String methodName;
    @NotNull
    private List<TypedValueInfo> args = new ArrayList<>();
    @NotNull
    private List<String> annotations = new ArrayList<>();
    private String modifiers;
    @NotNull
    private String returnType;

    RemoteMethodInfo(@NotNull String serviceName,
                            @NotNull String methodName,
                            @NotNull List<TypedValueInfo> args,
                            @NotNull List<String> annotations,
                            String modifiers,
                            @NotNull String returnType
    ) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        this.annotations = annotations;
        this.modifiers = modifiers;
        this.returnType = returnType;
    }

    public RemoteMethodInfo() {}

    public static RemoteMethodInfoBuilder builder() {
        return new RemoteMethodInfoBuilder();
    }

    public @NotNull String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public @NotNull List<TypedValueInfo> getArgs() {
        return args;
    }

    public @NotNull List<String> getAnnotations() {
        return annotations;
    }

    public String getModifiers() {
        return modifiers;
    }

    public @NotNull String getReturnType() {
        return returnType;
    }

    public void setServiceName(@NotNull String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMethodName(@NotNull String methodName) {
        this.methodName = methodName;
    }

    public void setArgs(@NotNull List<TypedValueInfo> args) {
        this.args = args;
    }

    public void setAnnotations(@NotNull List<String> annotations) {
        this.annotations = annotations;
    }

    public void setModifiers(String modifiers) {
        this.modifiers = modifiers;
    }

    public void setReturnType(@NotNull String returnType) {
        this.returnType = returnType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RemoteMethodInfo that = (RemoteMethodInfo) o;
        return Objects.equals(serviceName, that.serviceName)
                && Objects.equals(methodName, that.methodName)
                && Objects.equals(args, that.args)
                && Objects.equals(annotations, that.annotations)
                && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, methodName, args, annotations, returnType);
    }

    public String toString() {
        return "RemoteMethodInfo(serviceName=" + serviceName
                + ", methodName=" + methodName
                + ", args=" + args
                + ", annotations=" + annotations
                + ", modifiers=" + modifiers
                + ", returnType=" + returnType + ")";
    }

    static class RemoteMethodInfoDeserializer extends StdDeserializer<RemoteMethodInfo> {
        private static final long serialVersionUID = -6939384938159722689L;

        RemoteMethodInfoDeserializer(final Class<?> vc) {
            super(vc);
        }

        RemoteMethodInfoDeserializer() {
            this(null);
        }

        @Override
        public RemoteMethodInfo deserialize(final JsonParser p,
                                            final DeserializationContext ctx
        ) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            final ObjectMapper mapper = new ObjectMapper();
            List<TypedValueInfo> args = mapper.readerFor(new TypeReference<List<TypedValueInfo>>() {})
                                              .readValue(node.get("args"));
            String modifiers = node.get("modifiers").asText();
            String serviceName = node.get("serviceName").asText();
            String methodName = node.get("methodName").asText();
            List<String> annotations = mapper.readerFor(new TypeReference<List<String>>() {})
                                             .readValue(node.get("annotations"));
            String returnType = node.get("returnType").asText();
            return new RemoteMethodInfo(serviceName, methodName, args, annotations, modifiers, returnType);
        }
    }

    static RemoteMethodInfo fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(RemoteMethodInfo.class, new RemoteMethodInfoDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(json, RemoteMethodInfo.class);
    }

    public static class RemoteMethodInfoBuilder {
        private @NotNull String serviceName;
        private @NotNull String methodName;
        private @NotNull List<TypedValueInfo> args;
        private @NotNull List<String> annotations;
        private String modifiers;
        private @NotNull String returnType;

        public RemoteMethodInfo.RemoteMethodInfoBuilder serviceName(@NotNull String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public RemoteMethodInfo.RemoteMethodInfoBuilder methodName(@NotNull String methodName) {
            this.methodName = methodName;
            return this;
        }

        public RemoteMethodInfo.RemoteMethodInfoBuilder args(@NotNull List<TypedValueInfo> args) {
            this.args = args;
            return this;
        }

        public RemoteMethodInfo.RemoteMethodInfoBuilder annotations(@NotNull List<String> annotations) {
            this.annotations = annotations;
            return this;
        }

        public RemoteMethodInfo.RemoteMethodInfoBuilder modifiers(String modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        public RemoteMethodInfo.RemoteMethodInfoBuilder returnType(@NotNull String returnType) {
            this.returnType = returnType;
            return this;
        }

        public RemoteMethodInfo build() {
            return new RemoteMethodInfo(serviceName, methodName, args, annotations, modifiers, returnType);
        }
    }
}
