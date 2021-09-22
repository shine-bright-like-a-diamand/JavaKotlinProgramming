public class TreeVisitor implements ExpressionVisitor {

    private TreeVisitor() {}

    public static TreeVisitor INSTANCE = new TreeVisitor();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        int depth_of_lexpr = (int) expr.getLeft().accept(this);
        int depth_of_rexpr = (int) expr.getRight().accept(this);
        return depth_of_lexpr > depth_of_rexpr ? depth_of_lexpr + 1 : depth_of_rexpr + 1;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Object visitVariable(Variable expr) {
        return 1;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return (int) expression.getExpr().accept(this) + 1;
    }
}
