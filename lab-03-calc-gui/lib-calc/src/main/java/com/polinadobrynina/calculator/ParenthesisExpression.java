package com.polinadobrynina.calculator;

public interface ParenthesisExpression extends Expression {
    Expression getExpr();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitParenthesis(this);
    }
}
