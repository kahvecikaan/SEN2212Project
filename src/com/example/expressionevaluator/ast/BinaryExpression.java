package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.evaluator.Visitor;
import com.example.expressionevaluator.token.Token;

public class BinaryExpression extends Expression{
    private final Expression left;
    private final Expression right;
    private final Token operator;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Token getOperator() {
        return operator;
    }
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
