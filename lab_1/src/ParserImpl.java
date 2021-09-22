import java.util.ArrayList;
import java.util.Stack;

public class ParserImpl implements Parser {

    private ArrayList<Expression> tokenize(String expr) {
        ArrayList<Expression> tokens = new ArrayList<>();
        int parentheses_counter = 0;
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (ch == ' ') {
                continue;
            }
            Expression token;
            if (Character.isLetter(ch)) {
                token = new Variable(String.valueOf(ch));
            } else if (ch == '(') {
                token = new ParenthesisExpressionImpl(BracketType.OPEN);
                parentheses_counter++;
            } else if (ch == ')') {
                token = new ParenthesisExpressionImpl(BracketType.CLOSE);
                parentheses_counter--;
                if (parentheses_counter < 0) {
                    throw new RuntimeException("Wrong parentheses placement");
                }
            } else if (isLiteral(ch) || ch =='.') {
                StringBuilder builder = new StringBuilder();
                builder.append(ch);
                while (true) {
                    if (i != expr.length() - 1 && (isLiteral(expr.charAt(i + 1)) || expr.charAt(i + 1) == '.')) {
                        i++;
                        builder.append(expr.charAt(i));
                    } else {
                        break;
                    }
                }
                token = new LiteralImpl(Double.parseDouble(builder.toString()));
            } else if (isOperator(ch)) {
                BinOpKind bin_op_kind = switch (ch) {
                    case '+' -> BinOpKind.ADD;
                    case '-' -> BinOpKind.SUB;
                    case '*' -> BinOpKind.MUL;
                    case '/' -> BinOpKind.DIV;
                    default -> throw new RuntimeException("Invalid char in input");
                };
                token = new BinaryExpressionImpl(bin_op_kind, null, null);
            } else {
                throw new RuntimeException("Invalid char in input");
            }
            tokens.add(token);
        }
        if (parentheses_counter != 0) {
            throw new RuntimeException("Wrong parentheses placement");
        }
        return tokens;
    }

    private ArrayList<Expression> toPostfix(ArrayList<Expression> tokens) {
        ArrayList<Expression> result = new ArrayList<>();
        Stack<Expression> stack = new Stack<>();
        for (var token : tokens) {
            if (token instanceof LiteralImpl || token instanceof Variable) {
                result.add(token);
            } else if (token instanceof BinaryExpressionImpl) {
                while (((!stack.empty() && Priority(stack.peek()) >= Priority(token)))) {
                    result.add(stack.peek());
                    stack.pop();
                }
                stack.push(token);
            } else if (token instanceof ParenthesisExpressionImpl bracket) {
                if (bracket.bracket_type == BracketType.OPEN) {
                    stack.push(token);
                    result.add(token);
                } else {
                    while ((!(stack.peek() instanceof ParenthesisExpressionImpl)) ||
                            (((ParenthesisExpressionImpl) stack.peek()).bracket_type != BracketType.OPEN)) {
                        result.add(stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                    result.add(token);
                }
            }
        }
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private Expression toTree(ArrayList<Expression> postfix_tokens) {
        Stack<Expression> stack = new Stack<>();
        for (var token : postfix_tokens) {
            if (token instanceof ParenthesisExpressionImpl) {
                if (((ParenthesisExpressionImpl)token).bracket_type == BracketType.CLOSE) {
                    Expression l_expr = stack.pop();
                    stack.push(new ParenthesisExpressionImpl(l_expr, null));
                }
                continue;
            }
            if (!(token instanceof LiteralImpl || token instanceof Variable)) {
                Expression right_expr = stack.pop();
                Expression left_expr = stack.pop();
                stack.push(new BinaryExpressionImpl(((BinaryExpressionImpl) token).getOperation(),
                        left_expr, right_expr));
            } else {
                stack.push(token);
            }
        }
        return stack.peek();
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private boolean isLiteral(char ch) {
        return '0' <= ch && ch <= '9';
    }

    private int Priority(Expression token) {
        if (token instanceof BinaryExpressionImpl expr) {
            if (expr.getOperation() == BinOpKind.MUL
                    || expr.getOperation() == BinOpKind.DIV) {
                return 1;
            } else if (expr.getOperation() == BinOpKind.ADD
                    || expr.getOperation() == BinOpKind.SUB) {
                return 0;
            }
        }
        return -1;
    }

    private void checkExprValidity(ArrayList<Expression> tokens) {
        if (tokens.size() % 2 != 1) {
            throw new RuntimeException("Invalid expression");
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (i == tokens.size() - 1) {
                break;
            }
            if (((tokens.get(i) instanceof LiteralImpl) || (tokens.get(i) instanceof Variable)) &&
                    ((tokens.get(i + 1) instanceof LiteralImpl) || (tokens.get(i + 1) instanceof Variable)) ) {
                throw new RuntimeException("Invalid expression");
            }
            if (((tokens.get(i) instanceof BinaryExpressionImpl) || (tokens.get(i) instanceof ParenthesisExpressionImpl
                    && ((ParenthesisExpressionImpl)tokens.get(i)).bracket_type == BracketType.OPEN)) &&
                    ((tokens.get(i + 1) instanceof BinaryExpressionImpl))) {
                throw new RuntimeException("Invalid expression");
            }
        }
    }

    @Override
    public Expression parseExpression(String input) {
        ArrayList<Expression> tokens = tokenize(input);
        checkExprValidity(tokens);
        return toTree(toPostfix(tokens));
    }
}
