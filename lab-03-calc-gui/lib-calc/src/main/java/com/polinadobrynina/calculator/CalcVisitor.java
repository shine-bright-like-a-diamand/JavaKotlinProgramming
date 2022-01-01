package com.polinadobrynina.calculator;

import java.util.Map;

public class CalcVisitor implements ExpressionVisitor {
    private final Map<String, Double> mValues;

    public CalcVisitor(Map<String, Double> values) {
        mValues = values;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expression) {
        Double ret_val = switch (expression.getOperation()) {
            case ADD -> (Double) expression.getLeft().accept(this) + (Double) expression.getRight().accept(this);
            case SUB -> (Double) expression.getLeft().accept(this) - (Double) expression.getRight().accept(this);
            case MUL -> (Double) expression.getLeft().accept(this) * (Double) expression.getRight().accept(this);
            case DIV -> (Double) expression.getLeft().accept(this) / (Double) expression.getRight().accept(this);
        };

        if (ret_val.isNaN() || ret_val.isInfinite()) {
            throw new RuntimeException("Invalid result");
        }

        return ret_val;
    }

    @Override
    public Object visitLiteral(Literal expression) {
        return expression.getValue();
    }

    @Override
    public Object visitVariable(Variable expression) {
        return  mValues.get((expression.getName()));
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this);
    }
}

