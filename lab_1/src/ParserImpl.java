import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class ParserImpl implements Parser {

    enum TokenType {
        NUM,
        VAR,
        OP,
        BR
    }

    private class Token {
        Token(String str, TokenType token_type){this.str = str; this.tokenType = token_type;}

        String str;
        TokenType tokenType;
    }

    private ArrayList<Token> tokenize(String expr) {
        ArrayList<Token> tokens = new ArrayList<>();
        int parentheses_counter = 0;
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (ch == ' ') {
                continue;
            }
            Token token;
            if (Character.isLetter(ch)) {
                token = new Token(String.valueOf(ch), TokenType.VAR);
            } else if (ch == '(') {
                token = new Token(String.valueOf(ch), TokenType.BR);
                parentheses_counter++;
            } else if (ch == ')') {
                token = new Token(String.valueOf(ch), TokenType.BR);
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
                token = new Token(builder.toString(), TokenType.NUM);
            } else if (isOperator(ch)) {
                token = new Token(String.valueOf(ch), TokenType.OP);
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

    private ArrayList<Token> toPostfix(ArrayList<Token> tokens) {
        ArrayList<Token> result = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        for (var token : tokens) {
            if (token.tokenType == TokenType.NUM || token.tokenType == TokenType.VAR) {
                result.add(token);
            } else if (token.tokenType == TokenType.OP) {
                while (((!stack.empty() && Priority(stack.peek()) >= Priority(token)))) {
                    result.add(stack.peek());
                    stack.pop();
                }
                stack.push(token);
            } else if (token.tokenType == TokenType.BR) {
                if (Objects.equals(token.str, "(")) {
                    stack.push(token);
                } else {
                    while ((!(stack.peek().tokenType == TokenType.BR)) ||
                            (Objects.equals(stack.peek().str, ")"))) {
                        result.add(stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                }
                result.add(token);
            }
        }
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private Expression toTree(ArrayList<Token> postfix_tokens) {
        Stack<Expression> stack = new Stack<>();
        for (var token : postfix_tokens) {
            if (token.tokenType == TokenType.BR) {
                if (Objects.equals(token.str, ")")) {
                    Expression l_expr = stack.pop();
                    stack.push(new ParenthesisExpressionImpl(l_expr));
                }
                continue;
            }
            if (!(token.tokenType == TokenType.NUM || token.tokenType == TokenType.VAR)) {
                Expression right_expr = stack.pop();
                Expression left_expr = stack.pop();
                stack.push(new BinaryExpressionImpl(getOperation(token), left_expr, right_expr));
            } else {
                stack.push(tokenToExpression(token));
            }
        }
        return stack.peek();
    }

    private BinOpKind getOperation(Token token) {
        return switch (token.str) {
            case "+" -> BinOpKind.ADD;
            case "-" -> BinOpKind.SUB;
            case "/" -> BinOpKind.DIV;
            case "*" -> BinOpKind.MUL;
            default -> null;
        };
    }
    private Expression tokenToExpression(Token token) {
       return switch (token.tokenType) {
           case NUM -> new LiteralImpl(Double.parseDouble(token.str));
           case VAR -> new Variable(token.str);
           case OP -> null;
           case BR -> null;
       };
    }
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private boolean isLiteral(char ch) {
        return '0' <= ch && ch <= '9';
    }

    private int Priority(Token token) {
        if (token.tokenType == TokenType.OP) {
            if (Objects.equals(token.str, "*")
                    || Objects.equals(token.str, "/")) {
                return 1;
            } else if (Objects.equals(token.str, "+")
                    || Objects.equals(token.str, "-")) {
                return 0;
            }
        }
        return -1;
    }

    private void checkExprValidity(ArrayList<Token> tokens) {
        if (tokens.size() % 2 != 1) {
            throw new RuntimeException("Invalid expression");
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (i == tokens.size() - 1) {
                break;
            }
            if (((tokens.get(i).tokenType ==  TokenType.NUM) || (tokens.get(i).tokenType == TokenType.VAR)) &&
                    ((tokens.get(i+1).tokenType ==  TokenType.NUM) || (tokens.get(i+1).tokenType == TokenType.VAR))) {
                throw new RuntimeException("Invalid expression");
            }
            if (((tokens.get(i).tokenType == TokenType.OP) || (Objects.equals(tokens.get(i).str, "(")))
                    && (tokens.get(i + 1).tokenType == TokenType.OP)) {
                throw new RuntimeException("Invalid expression");
            }
        }
    }

    @Override
    public Expression parseExpression(String input) {
        ArrayList<Token> tokens = tokenize(input);
        checkExprValidity(tokens);
        return toTree(toPostfix(tokens));
    }
}
