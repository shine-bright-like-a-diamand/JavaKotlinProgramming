import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VariableVisitor implements ExpressionVisitor<Map<String, Double>> {
    private final Map<String, Double> variables = new HashMap<>();

    @Override
    public Map<String, Double> visitBinaryExpression(BinaryExpression expr) {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
        return variables;
    }

    @Override
    public Map<String, Double> visitLiteral(Literal expr) {
        return variables;
    }

    @Override
    public Map<String, Double> visitVariable(Variable expr) {
        if (!variables.containsKey(expr.getName())) {
            System.out.print("value for '" + expr.getName() + "': ");
            Scanner in = new Scanner(System.in);
            variables.put(expr.getName(), Double.parseDouble(in.next()));
        }
        return variables;
    }

    @Override
    public Map<String, Double> visitParenthesis(ParenthesisExpression expression) {
        return expression.getExpr().accept(this);
    }
}
