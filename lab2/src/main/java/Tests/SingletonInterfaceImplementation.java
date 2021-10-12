package Tests;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonInterfaceImplementation implements Interface {
    @Inject
    public SingletonInterfaceImplementation() {}

    @Override
    public String getName() {
        return "singleton impl created";
    }
}
