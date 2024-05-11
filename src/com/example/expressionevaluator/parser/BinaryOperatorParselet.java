package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.BinaryExpression;
import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.ast.IntegerLiteral;
import com.example.expressionevaluator.token.Token;

public class BinaryOperatorParselet implements InfixParselet {
    private final int precedence;

    public BinaryOperatorParselet(int precedence) {
        this.precedence = precedence;
    }
    @Override
    public Expression parse(PrattParser parser, Expression left, Token token) {
        Expression right = parser.parseExpression(precedence);

        if(token.type == Token.TokenType.SLASH) {
            if(right instanceof IntegerLiteral && ((IntegerLiteral) right).getValue() == 0) {
                throw new RuntimeException("Division by zero");
            }
        }

        return new BinaryExpression(left, token, right);
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }
}
