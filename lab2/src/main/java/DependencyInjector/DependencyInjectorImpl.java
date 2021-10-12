package DependencyInjector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

public class DependencyInjectorImpl implements DependencyInjector {
    boolean mIsRegistrationComplete = false;

    final Set<Class<?>> mRegisteredClasses = new HashSet<>();
    final Map<Class<?>, Class<?>> mInterfacesImplementation = new HashMap<>();
    final Map<Class<?>, Constructor<?>> mConstructor = new HashMap<>();

    final Set<Class<?>> mSingletons = new HashSet<>();
    final Map<Class<?>, Object> mSingletonInstance = new HashMap<>();


    @Override
    public void register(Class<?> cl) {

        if (mIsRegistrationComplete) {
            throw new RuntimeException("Can't register. Registration called after complete registration");
        }
        if (mRegisteredClasses.contains(cl)) {
            throw new RuntimeException(cl + " has already registered");
        }
        if (cl.isInterface()) {
            throw new IllegalArgumentException(cl + " is an interface, but class was expected.");
        }

        mRegisteredClasses.add(cl);
        var constructor = getInjectConstructor(cl);
        mConstructor.put(cl, constructor);

        if (constructor.isAnnotationPresent(Singleton.class)) {
            mSingletons.add(cl);
        }
    }

    @Override
    public void register(Class<?> interf, Class<?> implCl) {

        if (mIsRegistrationComplete) {
            throw new RuntimeException("Can't register. Registration called after complete registration");
        }
        if (mInterfacesImplementation.containsKey(interf)) {
            throw new RuntimeException(interf + " has already registered");
        }
        if (!interf.isInterface()) {
            throw new IllegalArgumentException(interf + " isn't an interface");
        }
        if (implCl.isInterface()) {
            throw new IllegalArgumentException(implCl + " is an interface, but class was expected");
        }
        if (!interf.isAssignableFrom(implCl)) {
            throw new IllegalArgumentException(implCl + " isn't implements " + interf);
        }

        mInterfacesImplementation.put(interf, implCl);
        register(implCl);

    }

    @Override
    public void completeRegistration() {

        if (mIsRegistrationComplete) {
            throw new RuntimeException("Can't complete registration. Registration was completed");
        }
        checkGraphValidity();
        mIsRegistrationComplete = true;

    }

    public void checkGraphValidity() {
        int count = 0;
        Map<Class<?>, Integer> visitedClasses = new HashMap<>();
        Stack<Class<?>> stack = new Stack<>();

        for (var key : mConstructor.keySet()) {
            if (visitedClasses.containsKey(key)) {
                continue;
            }
            visitedClasses.put(key, ++count);
            stack.push(key);
            while(!stack.empty()) {
                var top = stack.pop();
                for (Class<?> argType : mConstructor.get(top).getParameterTypes()){
                    if (!mConstructor.containsKey(argType)) {
                        throw new RuntimeException("A registered class has an unregistered dependency");
                    }
                    if (visitedClasses.containsKey(argType)) {
                        if (visitedClasses.get(argType) == count) {
                            throw new RuntimeException("Dependencies graph has cycles");
                        }
                        continue;
                    }
                    stack.push(argType);
                    visitedClasses.put(argType, count);
                }
            }
        }
    }

    @Override
    public <T> T resolve(Class<T> cl) {

        if (!mIsRegistrationComplete) {
            throw new RuntimeException("Can't resolve. Registration isn't completed");
        }

        if (cl.isInterface()) {
            if (!mInterfacesImplementation.containsKey(cl)) {
                throw new RuntimeException(cl + " isn't registered");
            }
            return cl.cast(getInstance(mInterfacesImplementation.get(cl)));
        } else {
            if (!mRegisteredClasses.contains(cl)) {
                throw new RuntimeException(cl + " isn't registered");
            }
            return getInstance(cl);
        }

    }

    private <T> T getInstance(Class<T> cl) {

        if (mSingletonInstance.containsKey(cl)) {
            return cl.cast(mSingletonInstance.get(cl));
        }

        return constructInstance(cl);
    }

    private <T> T constructInstance(Class<T> cl) {
        var constructor = mConstructor.get(cl);
        var parameters = mConstructor.get(cl).getParameterTypes();
        var parametersValues = new ArrayList<>();

        for (var param : parameters) {
            parametersValues.add(resolve(param));
        }

        try {
            var instance = cl.cast(constructor.newInstance(parametersValues));
            if (mSingletons.contains(cl)) {
                mSingletonInstance.put(cl, instance);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Constructor<?> getInjectConstructor(Class<?> cl) {

        var injectConstructors = Arrays.stream(cl.getConstructors()).
                filter(constr -> constr.isAnnotationPresent(Inject.class)).
                collect(Collectors.toList());

        if (injectConstructors.size() == 0) {
            throw new RuntimeException(cl + " doesn't have public @Inject constructors");
        }
        if (injectConstructors.size() > 1) {
            throw new RuntimeException(cl + " has more than one @Inject constructor");
        }
        return injectConstructors.get(0);
    }

}
