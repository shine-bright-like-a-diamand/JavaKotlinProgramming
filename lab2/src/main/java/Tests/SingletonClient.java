package Tests;

import javax.inject.Inject;

public class SingletonClient {

    @Inject
    public SingletonClient(SimpleSingleton service) {
        mService = service;
    }

    public SingletonClient() {
        mService = null;
    }

    public String getName() {
        return "singleton client";
    }

    public SimpleSingleton getService() {
        return mService;
    }

    private final SimpleSingleton mService;
}
