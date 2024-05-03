package com.example.expressionevaluator.evaluator;

import com.example.expressionevaluator.ast.AbsExpression;
import com.example.expressionevaluator.ast.BinaryExpression;
import com.example.expressionevaluator.ast.IdentifierExpression;
import com.example.expressionevaluator.ast.IntegerLiteral;
import com.example.expressionevaluator.env.Environment;
import com.example.expressionevaluator.token.Token;

public class EvaluationVisitor implements Visitor<Integer> {
    private final Environment environment;

    public EvaluationVisitor(Environment environment) {
        this.environment = environment;
    }
    @Override
    public Integer visitIntegerLiteral(IntegerLiteral node) {
        return node.getValue();
    }

    @Override
    public Integer visitBinaryExpression(BinaryExpression binaryExpression) {
        Token.TokenType operator = binaryExpression.getOperator().type;
        int left = binaryExpression.getLeft().accept(this);
        int right = binaryExpression.getRight().accept(this);
        switch (operator) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case ASTERISK:
                return left * right;
            case SLASH:
                return left / right;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    @Override
    public Integer visitAbsExpression(AbsExpression absExpression) {
        int value = absExpression.getExpression().accept(this);
        return Math.abs(value);
    }

    @Override
    public Integer visitIdentifierExpression(IdentifierExpression node) {
        String name = node.getName();
        if(environment.isDefined(name)) {
            return environment.getValue(name);
        } else {
            throw new RuntimeException("Undefined variable " + name);
        }
    }
}
