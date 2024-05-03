package com.example.expressionevaluator.evaluator;

import com.example.expressionevaluator.ast.AbsExpression;
import com.example.expressionevaluator.ast.BinaryExpression;
import com.example.expressionevaluator.ast.IdentifierExpression;
import com.example.expressionevaluator.ast.IntegerLiteral;

public interface Visitor<R> {
    R visitIntegerLiteral(IntegerLiteral node);
    R visitBinaryExpression(BinaryExpression node);
    R visitAbsExpression(AbsExpression node);
    R visitIdentifierExpression(IdentifierExpression node);
}
