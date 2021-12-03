public class ToStringVisitor implements ExpressionVisitor<String> {
    private ToStringVisitor() {}

    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        return expr.getLeft().accept(this) +
                switch (expr.getOperation()) {
                    case ADD -> " + ";
                    case SUB -> " - ";
                    case MUL -> " * ";
                    case DIV -> " / ";
                } +
                expr.getRight().accept(this);
    }

    @Override
    public String visitLiteral(Literal expr) {
        return String.valueOf(expr.getValue());
    }

    @Override
    public String visitVariable(Variable expr) {
        return expr.getName();
    }

    @Override
    public String visitParenthesis(ParenthesisExpression expression) {
        return "(" + expression.getExpr().accept(this) + ")";

    }

    public static ExpressionVisitor<String> INSTANCE = new ToStringVisitor();
}
