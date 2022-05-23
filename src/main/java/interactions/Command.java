package interactions;

import java.lang.reflect.*;
import java.util.*;

public class Command {

    public enum State {
        VISIBLE, HIDDEN, BLOCKED
    }

    public String name() {
        return name;
    }

    private final String signature;
    private final String name;
    private final Object owner;         //instance which holds the method
    private final Method method;
    private String description;
    private State state;

    public Command.State state() {
        return state;
    }

    public Command setState(final Command.State state) {
        this.state = state;
        return this;
    }

    public Method method() {
        return method;
    }

    public String signature() {
        return signature;
    }

    public String description() {
        return description;
    }

    public Command setDescription(final String description) {
        this.description = description;
        return this;
    }

    public Command(final String signature, String name, final Object owner) {
        this.name = name;
        this.signature = signature;
        this.owner = owner;
        this.method = Arrays.stream(owner.getClass().getMethods()).filter(method -> method.getName().equals(name)).findFirst().orElse(null);
    }

    public void call(Object... args) {

        if (method != null && state != State.BLOCKED) {
            try {
                if (method.getParameterCount() == args.length) {
                    method.invoke(owner, args);
                }
                else if (!method.isVarArgs()) {
                    method.invoke(owner);
                }
                else {
                    printf("Invalid amount of input parameters: %d, expected %d %n", args.length, method.getParameterCount());
                }

            }
            catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        else if (state == State.BLOCKED){
            printf("Method %s is not allowed %n", signature);
        }
        else {
            printf("Method %s does not exist %n", signature);
        }
    }

    public List<String> arguments() {
        return Arrays.stream(method.getParameters()).map(Parameter::getName).toList();
    }

    private void printf(String string, Object... args) {
        System.out.printf(string, args);
    }


}
