package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.ast.IntegerLiteral;
import com.example.expressionevaluator.token.Token;

public class IntegerParselet implements PrefixParselet{
    @Override
    public Expression parse(PrattParser parser, Token token) {
        return new IntegerLiteral(Integer.parseInt(token.literal));
    }
}
