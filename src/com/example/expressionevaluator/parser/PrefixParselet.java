package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.token.Token;

public interface PrefixParselet {
    Expression parse(PrattParser parser, Token token);
}
