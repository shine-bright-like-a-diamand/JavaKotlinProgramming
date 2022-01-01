package com.polinadobrynina.calculator;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor<String>{
    private DebugRepresentationExpressionVisitor() {}

    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        String leftResult = expr.getLeft().accept(this);
        String rightResult = expr.getRight().accept(this);
        String result = switch (expr.getOperation()) {
            case ADD -> "add(";
            case SUB -> "sub(";
            case MUL -> "mul(";
            case DIV -> "div(";
        };
        return result + leftResult + ", " + rightResult + ")";
    }

    @Override
    public String visitLiteral(Literal expr) {
        return "'" + expr.getValue() + "'";
    }

    @Override
    public String visitVariable(Variable expr) {
        return "var[" +expr.getName() + "]";
    }

    @Override
    public String visitParenthesis(ParenthesisExpression expression) {
        return "paran-expr(" + expression.getExpr().accept(this) + ")";
    }

    public static ExpressionVisitor<String> INSTANCE = new DebugRepresentationExpressionVisitor();
}
