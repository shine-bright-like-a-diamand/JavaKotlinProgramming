public class ParenthesisExpressionImpl implements ParenthesisExpression {
    public final BracketType bracket_type;
    private Expression mExpression;

    ParenthesisExpressionImpl(Expression expression, BracketType bracket_type) {
        mExpression = expression;
        this.bracket_type = bracket_type;
    }

    public ParenthesisExpressionImpl(BracketType bracket) {
        this.bracket_type = bracket;
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
