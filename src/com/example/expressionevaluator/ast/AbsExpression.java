package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;

public class AbsExpression extends Expression {
    private final Expression expression;

    public AbsExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAbsExpression(this);
    }
}
