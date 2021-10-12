package Tests;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SimpleSingleton {
    @Inject
    public SimpleSingleton() {}

    public String getName() {return mName;}

    private final String mName = "simple singleton";
}
