package com.polinadobrynina.calculator;

public interface Variable extends Expression {
    String getName();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }
}
