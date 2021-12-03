public class DebugRepresentationExpressionVisitor implements ExpressionVisitor{
    private DebugRepresentationExpressionVisitor() {}

    public static DebugRepresentationExpressionVisitor INSTANCE = new DebugRepresentationExpressionVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        String left_result = (String) expr.getLeft().accept(this);
        String right_result = (String) expr.getRight().accept(this);
        String result = switch (expr.getOperation()) {
            case ADD -> "add(";
            case SUB -> "sub(";
            case MUL -> "mul(";
            case DIV -> "div(";
        };
        return result + left_result + ", " + right_result + ")";
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return "'" + expr.getValue() + "'";
    }

    @Override
    public Object visitVariable(Variable expr) {
        return "var[" +expr.getName() + "]";
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return "paran-expr(" + expression.getExpr().accept(this) + ")";
    }
}
