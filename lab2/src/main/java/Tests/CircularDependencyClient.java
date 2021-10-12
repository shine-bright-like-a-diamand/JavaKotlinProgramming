package Tests;

import javax.inject.Inject;

public class CircularDependencyClient {

    @Inject
    public CircularDependencyClient(FirstClass firstClass, SecondClass secondClass) {
        this.firstClass = firstClass;
        this.secondClass = secondClass;
    }

    private final FirstClass firstClass;
    private final SecondClass secondClass;

}
