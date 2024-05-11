package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;

public class AssignmentExpression extends Expression{
    private IdentifierExpression identifier;
    private Expression value;

    public AssignmentExpression(IdentifierExpression identifier, Expression value) {
        this.identifier = identifier;
        this.value = value;
    }

    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    public Expression getValue() {
        return value;
    }
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAssignmentExpression(this);
    }
}
