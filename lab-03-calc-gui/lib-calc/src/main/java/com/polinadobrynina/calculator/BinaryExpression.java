package com.polinadobrynina.calculator;


public interface BinaryExpression extends Expression {
    Expression getLeft();
    Expression getRight();
    BinOpKind getOperation();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
