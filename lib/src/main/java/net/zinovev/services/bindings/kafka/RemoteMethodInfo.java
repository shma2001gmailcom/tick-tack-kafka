package net.zinovev.services.bindings.kafka;


import javax.validation.constraints.NotNull;
import java.util.List;

public class RemoteMethodInfo {

    @NotNull
    private String serviceName;

    @NotNull
    private String methodName;

    @NotNull
    private List<String> args;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
