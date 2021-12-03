import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VariableVisitor implements ExpressionVisitor {
    private final Map<String, Double> variables = new HashMap<>();

    @Override
    public Object visitBinaryExpression(BinaryExpression expr) {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
        return variables;
    }

    @Override
    public Object visitLiteral(Literal expr) {
        return variables;
    }

    @Override
    public Object visitVariable(Variable expr) {
        if (!variables.containsKey(expr.getName())) {
            System.out.print("value for '" + expr.getName() + "': ");
            Scanner in = new Scanner(System.in);
            variables.put(expr.getName(), Double.parseDouble(in.next()));
        }
        return variables;
    }

    @Override
    public Object visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this);
    }
}
