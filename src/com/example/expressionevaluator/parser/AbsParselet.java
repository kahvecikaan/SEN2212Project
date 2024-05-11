package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.token.Token;
import com.example.expressionevaluator.ast.AbsExpression;

public class AbsParselet implements PrefixParselet {
    @Override
    public Expression parse(PrattParser parser, Token token) {
        parser.consume(Token.TokenType.LPAREN);
        Expression expression = parser.parseExpression(0);
        parser.consume(Token.TokenType.RPAREN);

        return new AbsExpression(expression);
    }
}
