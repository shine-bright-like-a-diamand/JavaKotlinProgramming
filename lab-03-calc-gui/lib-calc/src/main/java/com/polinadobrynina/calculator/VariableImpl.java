package com.polinadobrynina.calculator;

public class VariableImpl implements Variable {
    private final String name;

    public VariableImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
