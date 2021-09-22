import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ExpressionVisitor tree_visitor = TreeVisitor.INSTANCE;
        ExpressionVisitor debug_representation_visitor = DebugRepresentationExpressionVisitor.INSTANCE;
        ExpressionVisitor to_string_visitor = ToStringVisitor.INSTANCE;
        while (true) {
            try {
                System.out.print("enter expression: ");
                String input = in.nextLine();
                Expression expression = new ParserImpl().parseExpression(input);
                System.out.println("entered expression: " + expression.accept(to_string_visitor));
                System.out.println("tree: " + expression.accept(debug_representation_visitor));
                System.out.println("tree depth: " + expression.accept(tree_visitor));
                var variables = (Map<String, Double>) expression.accept(new VariableVisitor());
                double result = (Double) expression.accept(new ComputeExpressionVisitor(variables));
                System.out.println("result: " + result);
            } catch (Exception exception){
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }
}
