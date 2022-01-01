package com.polinadobrynina.calculator;

public class ParenthesisExpressionImpl implements ParenthesisExpression {
    private Expression mExpression;

    ParenthesisExpressionImpl(Expression expression) {
        mExpression = expression;
    }

    @Override
    public Expression getExpr() {
        return mExpression;
    }

}
