package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;

public class IntegerLiteral extends Expression{
    private final int value;

    public IntegerLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitIntegerLiteral(this);
    }
}
