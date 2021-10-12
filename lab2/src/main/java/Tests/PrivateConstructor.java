package Tests;

import javax.inject.Inject;

public class PrivateConstructor {

    public PrivateConstructor() {
        mName = "hahaha i have private constructor";
    }

    @Inject
    private PrivateConstructor(String string) {
        mName = string;
    }

    private final String mName;

}
