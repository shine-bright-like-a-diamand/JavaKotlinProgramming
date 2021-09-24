import java.util.Objects;

public class MainTests {

    static void expectEqual(double first, double second) {
        if (first != second) {
            System.err.println("Error: Expected value " + first + " is not equal to " + second);
        }
    }

    static void expectEqual(String first, String second) {
        if (!Objects.equals(first, second)) {
            System.err.println("Error: Expected value " + first + " is not equal to " + second);
        }
    }

    public static void main(String[] args) {
        String[] expressions = new String[]{"( 42   + 42) ", "42", "(42        * 42) + 5", "(42-2)*4",
                "((2 + 4 )/ 3 - 6  )*(5 - 6  ) "};
        String[] to_string_test = new String[]{"(42.0 + 42.0)", "42.0", "(42.0 * 42.0) + 5.0", "(42.0 - 2.0) * 4.0",
                "((2.0 + 4.0) / 3.0 - 6.0) * (5.0 - 6.0)"};
        String[] debug_representation_test = new String[] {"paran-expr(add('42.0', '42.0'))", "'42.0'",
               "add(paran-expr(mul('42.0', '42.0')), '5.0')",
               "mul(paran-expr(sub('42.0', '2.0')), '4.0')",
               "mul(paran-expr(sub(div(paran-expr(add('2.0', '4.0')), '3.0'), '6.0')), paran-expr(sub('5.0', '6.0')))"};
        double[] compute_test = new double[]{84, 42, 1769, 160, 4};
        for (int i = 0; i < 5; i++) {
            Expression expr = new ParserImpl().parseExpression(expressions[i]);
            expectEqual(to_string_test[i], (String) expr.accept(ToStringVisitor.INSTANCE));
            expectEqual(debug_representation_test[i],
                    (String) expr.accept(DebugRepresentationExpressionVisitor.INSTANCE));
            expectEqual(compute_test[i], (double) expr.accept(new ComputeExpressionVisitor(null)));
        }
    }
}
