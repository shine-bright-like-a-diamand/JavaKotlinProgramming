package Tests;

import javax.inject.Inject;

public class MiddleSingletonClient {

    @Inject
    public MiddleSingletonClient(SimpleSingleton firstService, SingletonClient secondService, SimpleSingleton mSimpleSingleton, SingletonClient mSingletonClient) {
        this.mSimpleSingleton = mSimpleSingleton;
        this.mSingletonClient = mSingletonClient;
    }

    public SimpleSingleton getSimpleSingleton() {
        return mSimpleSingleton;
    }

    public SingletonClient getSingletonClient() {
        return mSingletonClient;
    }

    public String getName() {
        return "middle singleton client";
    }

    private final SimpleSingleton mSimpleSingleton;
    private final SingletonClient mSingletonClient;

}
