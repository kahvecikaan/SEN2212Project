package com.example.expressionevaluator.evaluator;

import com.example.expressionevaluator.ast.*;

public interface Visitor<R> {
    R visitIntegerLiteral(IntegerLiteral node);
    R visitBinaryExpression(BinaryExpression node);
    R visitAbsExpression(AbsExpression node);
    R visitIdentifierExpression(IdentifierExpression node);
    R visitAssignmentExpression(AssignmentExpression node);
    R visitUnaryExpression(UnaryExpression node);
}
