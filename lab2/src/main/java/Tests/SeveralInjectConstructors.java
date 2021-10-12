package Tests;

import javax.inject.Inject;

public class SeveralInjectConstructors {
    @Inject
    public SeveralInjectConstructors() {
        mName = "hahaha several inject constructors";
    }

    @Inject
    public SeveralInjectConstructors(String string) {
        mName = string;
    }

    @Inject
    public SeveralInjectConstructors(String firstStr, String secondStr) {
        if (firstStr.length() > secondStr.length()) {
            mName = firstStr;
        } else {
            mName = "(((";
        }
    }

    private final String mName;
}
