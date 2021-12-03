import java.util.Map;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    private final Map<String, Double> variables;

    public ComputeExpressionVisitor(Map<String, Double> variables) {
        this.variables = variables;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        BinOpKind bin_op_kind = expr.getOperation();
        double left_expr = (double) expr.getLeft().accept(this);
        double right_expr = (double) expr.getRight().accept(this);
        double result;
        switch (bin_op_kind) {
            case ADD -> result = left_expr + right_expr;
            case SUB -> result = left_expr - right_expr;
            case MUL -> result = left_expr * right_expr;
            case DIV -> result = left_expr / right_expr;
            default -> throw new IllegalStateException("Unexpected value: " + bin_op_kind);
        }
        return result;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }

    @Override
    public Object visitVariable(Variable expr) {
        return variables.get(expr.getName());
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this);
    }
}
