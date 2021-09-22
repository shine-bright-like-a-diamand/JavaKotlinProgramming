public class ToStringVisitor implements ExpressionVisitor {
    private ToStringVisitor() {}

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
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
    public Object visitLiteral(Literal expr) {
        return String.valueOf(expr.getValue());
    }

    @Override
    public Object visitVariable(Variable expr) {
        return expr.getName();
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return "(" + expression.getExpr().accept(this) + ")";

    }

    public static ToStringVisitor INSTANCE = new ToStringVisitor();
}
