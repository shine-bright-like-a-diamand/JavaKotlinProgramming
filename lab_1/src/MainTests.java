import java.util.HashMap;

public class MainTests {

    static double compute(String input) {
        Expression expression;
        try {
            expression = new ParserImpl().parseExpression(input);
            return (double) expression.accept(new ComputeExpressionVisitor(new HashMap<>()));
        } catch (Exception exception) {
            System.out.println("Test failed");
        }
        return 0.0;
    }

    static void expectEqual(double first, double second) {
        if (first != second) {
            System.err.println("Error: Expected value " + first + " is not equal to " + second);
        }
    }

    public static void main(String[] args) {
        expectEqual(84, compute("(42+42)"));
        expectEqual(42, compute("42"));
        expectEqual(1769, compute("(42 * 42) + 5"));
        expectEqual(160, compute("(42 - 2) * 4"));
        expectEqual(4, compute("((2+4)/3-6)*(5-6)"));
    }
}
