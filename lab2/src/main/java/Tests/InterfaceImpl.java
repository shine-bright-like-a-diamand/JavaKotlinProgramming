package Tests;

import javax.inject.Inject;

public class InterfaceImpl implements Interface {

    @Inject
    public InterfaceImpl() {}


    @Override
    public String getName() {
        return "impl created";
    }
}
