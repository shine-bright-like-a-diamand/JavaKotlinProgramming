package com.polinadobrynina.calculator;

public class LiteralImpl implements Literal {
    private final double value;

    public LiteralImpl(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

}
