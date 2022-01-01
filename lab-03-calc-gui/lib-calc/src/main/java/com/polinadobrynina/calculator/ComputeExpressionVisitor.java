package com.polinadobrynina.calculator;

import java.util.Map;

public class ComputeExpressionVisitor implements ExpressionVisitor<Double> {
    private final Map<String, Double> variables;

    public ComputeExpressionVisitor(Map<String, Double> variables) {
        this.variables = variables;
    }

    @Override
    public Double visitBinaryExpression(BinaryExpression expr) {
        BinOpKind binOpKind = expr.getOperation();
        double leftExpr = expr.getLeft().accept(this);
        double rightExpr = expr.getRight().accept(this);
        double result;
        switch (binOpKind) {
            case ADD -> result = leftExpr + rightExpr;
            case SUB -> result = leftExpr - rightExpr;
            case MUL -> result = leftExpr * rightExpr;
            case DIV -> result = leftExpr / rightExpr;
            default -> throw new IllegalStateException("Unexpected value: " + binOpKind);
        }
        return result;
    }

    @Override
    public Double visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Double visitVariable(Variable expr) {
        return variables.get(expr.getName());
    }

    @Override
    public Double visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this);
    }
}
