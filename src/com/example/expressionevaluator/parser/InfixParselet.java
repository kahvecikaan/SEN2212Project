package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.token.Token;

public interface InfixParselet {
    Expression parse(PrattParser parser, Expression left, Token token);
    int getPrecedence();
}
