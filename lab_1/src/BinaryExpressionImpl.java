public class BinaryExpressionImpl implements BinaryExpression {
    private final BinOpKind bin_op_kind;
    private final Expression right_expr;
    private final Expression left_expr;

    public BinaryExpressionImpl(BinOpKind bin_op_kind,
                                Expression left_expr,
                                Expression right_expr) {
        this.bin_op_kind = bin_op_kind;
        this.right_expr = right_expr;
        this.left_expr = left_expr;
    }

    @Override
    public Expression getLeft() {
        return left_expr;
    }

    @Override
    public Expression getRight() {
        return right_expr;
    }

    @Override
    public BinOpKind getOperation() {
        return bin_op_kind;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }

}
