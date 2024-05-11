package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.AssignmentExpression;
import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.ast.IdentifierExpression;
import com.example.expressionevaluator.token.Token;

public class IdentifierParselet implements PrefixParselet {
    @Override
    public Expression parse(PrattParser parser, Token token) {
        if (parser.peek().type == Token.TokenType.ASSIGN) {
            parser.advanceIfNextIs(Token.TokenType.ASSIGN); // Advance currentToken if next token is ASSIGN
            Expression value = parser.parseExpression(0);
            return new AssignmentExpression(new IdentifierExpression(token.literal), value);
        }

        return new IdentifierExpression(token.literal);
    }

}
