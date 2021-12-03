public interface Literal extends Expression {
    double getValue();

    @Override
    default <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }
}
