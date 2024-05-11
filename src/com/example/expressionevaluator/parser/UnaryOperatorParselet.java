package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.ast.UnaryExpression;
import com.example.expressionevaluator.token.Token;

public class UnaryOperatorParselet implements  PrefixParselet {
    @Override
    public Expression parse(PrattParser parser, Token token) {
        // System.out.println("Parsing unary with token: " + token.literal);  // Diagnostic log
        Expression operand = parser.parseExpression(token.type.getPrecedence());
        return new UnaryExpression(token, operand);
    }
}
