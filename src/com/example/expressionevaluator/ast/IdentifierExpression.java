package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;

public class IdentifierExpression extends Expression {
    private final String name;

    public IdentifierExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitIdentifierExpression(this);
    }
}
