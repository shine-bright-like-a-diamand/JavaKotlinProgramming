package Tests;

import javax.inject.Inject;

public class SecondClass {

    @Inject
    public SecondClass(FirstClass service) {
        mService = service;
    }

    private final FirstClass mService;

}
