package com.polinadobrynina.calculator;

public interface ExpressionVisitor<T> {
    T visitBinaryExpression(BinaryExpression expr);
    T visitLiteral(Literal expr);
    T visitVariable(Variable expr);
    T visitParenthesis(ParenthesisExpression expression);
}
