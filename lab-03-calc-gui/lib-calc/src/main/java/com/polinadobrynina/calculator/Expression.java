package com.polinadobrynina.calculator;

public interface Expression {
    <T> T accept(ExpressionVisitor<T> visitor);
}
