package Tests;

import javax.inject.Inject;

public class FirstClass {
    @Inject
    public FirstClass(SecondClass service) {
        mService = service;
    }

    private final SecondClass mService;
}
