package com.polinadobrynina.calculator;

public class BinaryExpressionImpl implements BinaryExpression {
    private final BinOpKind binOpKind;
    private final Expression rightExpr;
    private final Expression leftExpr;

    public BinaryExpressionImpl(BinOpKind binOpKind,
                                Expression leftExpr,
                                Expression rightExpr) {
        this.binOpKind = binOpKind;
        this.rightExpr = rightExpr;
        this.leftExpr = leftExpr;
    }

    @Override
    public Expression getLeft() {
        return leftExpr;
    }

    @Override
    public Expression getRight() {
        return rightExpr;
    }

    @Override
    public BinOpKind getOperation() {
        return binOpKind;
    }

}
