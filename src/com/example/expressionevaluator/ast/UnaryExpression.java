package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;
import com.example.expressionevaluator.token.Token;

public class UnaryExpression extends Expression {
    private final Token operator;
    private final Expression operand;

    public UnaryExpression(Token operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getOperand() {
        return operand;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitUnaryExpression(this);
    }
}
