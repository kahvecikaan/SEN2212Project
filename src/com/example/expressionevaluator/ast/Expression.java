package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;

public abstract class Expression {
    public abstract <R> R accept(Visitor<R> visitor);
}
