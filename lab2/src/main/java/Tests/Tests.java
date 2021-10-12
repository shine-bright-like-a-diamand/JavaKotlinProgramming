package Tests;

import DependencyInjector.DependencyInjectorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Tests {

    private final DependencyInjectorImpl mDI = new DependencyInjectorImpl();

    @Test
    public void SeveralInjectorConstructors() {
        try {
            mDI.register(SeveralInjectConstructors.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "class Tests.SeveralInjectConstructors has more than one @Inject constructor");
        }
    }

    @org.junit.jupiter.api.Test
    public void PrivateInjectConstructor() {
        try {
            mDI.register(PrivateConstructor.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "class Tests.PrivateConstructor doesn't have public @Inject constructors");
        }
    }

    @org.junit.jupiter.api.Test
    public void NoInjectConstructor() {
        try {
            mDI.register(NoSuitableConstructors.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "class Tests.NoSuitableConstructors doesn't have public @Inject constructors");
        }
    }

    @org.junit.jupiter.api.Test
    public void SeveralImplementations() {
        try {
            mDI.register(Interface.class, InterfaceImpl.class);
            mDI.register(Interface.class, SingletonInterfaceImplementation.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "interface Tests.Interface has already registered");
        }
    }

    @org.junit.jupiter.api.Test
    public void WrongRegistrationParameters() {
        try {
            mDI.register(Interface.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "interface Tests.Interface is an interface, but class was expected.");
        }

        try {
            mDI.register(InterfaceImpl.class, ChildClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "class Tests.InterfaceImpl isn't an interface");
        }

        try {
            mDI.register(Interface.class, AlsoInterface.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "interface Tests.AlsoInterface is an interface, but class was expected");
        }

        try {
            mDI.register(Interface.class, SimpleSingleton.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "class Tests.SimpleSingleton isn't implements interface Tests.Interface");
        }

    }

    @org.junit.jupiter.api.Test
    public void CircularDependency() {
        try {
            mDI.register(FirstClass.class);
            mDI.register(SecondClass.class);
            mDI.completeRegistration();
            mDI.resolve(FirstClass.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Dependencies graph has cycles");
        }

        try {
            mDI.register(CircularDependencyClient.class);
            mDI.completeRegistration();
            mDI.resolve(CircularDependencyClient.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Dependencies graph has cycles");
        }
    }

    @org.junit.jupiter.api.Test
    public void NotRegistered() {
        try {
            mDI.resolve(MiddleSingletonClient.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), "Can't resolve. Registration isn't completed");
        }
    }

    @org.junit.jupiter.api.Test
    public void CompleteRegistration() {
        mDI.completeRegistration();
        try {
            mDI.register(SingletonClient.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "Can't register. Registration called after complete registration");
        }

        try {
            mDI.register(Interface.class, InterfaceImpl.class);
            Assertions.assertEquals("Doesn't", "work");
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(),
                    "Can't register. Registration called after complete registration");
        }
    }
}
