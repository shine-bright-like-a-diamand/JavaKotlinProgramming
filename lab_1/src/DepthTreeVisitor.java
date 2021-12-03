public class DepthTreeVisitor implements ExpressionVisitor<Integer> {

    private DepthTreeVisitor() {}

    @Override
    public Integer visitBinaryExpression(BinaryExpression expr) {
        int depthOfLexpr = expr.getLeft().accept(this);
        int depthOfRexpr = expr.getRight().accept(this);
        return depthOfLexpr > depthOfRexpr ? depthOfLexpr + 1 : depthOfRexpr + 1;
    }

    @Override
    public Integer visitLiteral(Literal expr) {
        return 1;
    }

    @Override
    public Integer visitVariable(Variable expr) {
        return 1;
    }

    @Override
    public Integer visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this) + 1;
    }

    public static ExpressionVisitor<Integer> INSTANCE = new DepthTreeVisitor();
}
