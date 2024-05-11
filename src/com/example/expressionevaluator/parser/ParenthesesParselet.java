package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.token.Token;

public class ParenthesesParselet implements PrefixParselet {
    @Override
    public Expression parse(PrattParser parser, Token token) {
        // Parse the expression inside the parentheses
        Expression inner = parser.parseExpression(0);  // Parse with the lowest precedence to capture the entire inner expression

        // Expect a closing parenthesis next
        if (parser.getCurrentTokenType() != Token.TokenType.RPAREN) {
            throw new RuntimeException("Expected closing parenthesis, found: " + parser.peek().literal);
        }

        parser.consume(Token.TokenType.RPAREN); // Consume the closing parenthesis
        return inner;
    }
}
