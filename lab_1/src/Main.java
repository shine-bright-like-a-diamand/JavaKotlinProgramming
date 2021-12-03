import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nenter expression: ");
                String input = in.nextLine();
                Expression expression = new ParserImpl().parseExpression(input);
                System.out.println("entered expression: " + expression.accept(ToStringVisitor.INSTANCE));
                System.out.println("tree: " + expression.accept(DebugRepresentationExpressionVisitor.INSTANCE));
                System.out.println("tree depth: " + expression.accept(DepthTreeVisitor.INSTANCE));
                var variables = (Map<String, Double>) expression.accept(new VariableVisitor());
                double result = (Double) expression.accept(new ComputeExpressionVisitor(variables));
                System.out.println("result: " + result);
            } catch (Exception exception){
                System.out.println(exception.getMessage() + "\n");
            }
        }
    }
}
