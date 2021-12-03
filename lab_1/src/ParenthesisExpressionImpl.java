public class ParenthesisExpressionImpl implements ParenthesisExpression {
    private Expression mExpression;

    ParenthesisExpressionImpl(Expression expression) {
        mExpression = expression;
    }

    @Override
    public Expression getExpr() {
        return mExpression;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
}
