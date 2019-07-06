package net.zinovev.services.bindings.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static java.lang.Thread.currentThread;
import static org.springframework.util.ClassUtils.forName;

@SuppressWarnings("unused")
public class TypedValueInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private String type;
    private String value;
    @JsonIgnore
    private ObjectMapper mapper = new ObjectMapper();

    public TypedValueInfo(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public TypedValueInfo() {
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TypedValueInfo that = (TypedValueInfo) o;
        return Objects.equals(type, that.type) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    public String toString() {
        return "TypedValueInfo(type=" + type
                + ", value=" + value + ")";
    }

    public static TypedValueInfo fromJson(String json)  {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, TypedValueInfo.class);
        } catch (IOException e) {
            return new TypedValueInfo(e.getMessage(), String.class.getName());
        }
    }

    public Object resolve() {
        try {
            final Class<?> valueType = forName(type, currentThread().getContextClassLoader());
            final String readable = valueType == String.class ? "\"" + value + "\"" : value;
            return mapper.readValue(readable, valueType);
        } catch (IOException | ClassNotFoundException e) {
            return new IllegalArgumentException();
        }
    }

    public Class<?> resolveType() {
        try {
            return forName(type, currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            return ClassNotFoundException.class;
        }
    }
}
