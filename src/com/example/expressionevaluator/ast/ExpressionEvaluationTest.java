package com.example.expressionevaluator.ast;

import com.example.expressionevaluator.env.Environment;
import com.example.expressionevaluator.evaluator.EvaluationVisitor;
import com.example.expressionevaluator.token.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionEvaluationTest {
    @Test
    public void testIntegerLiteralEvaluation() {
        Expression expression = new IntegerLiteral(42);
        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(42, result);
    }

    @Test
    public void testBinaryExpressionEvaluation() {
        Expression left = new IntegerLiteral(5);
        Expression right = new IntegerLiteral(3);
        Expression addition = new BinaryExpression(left, new Token(Token.TokenType.PLUS, "+"), right);
        Expression multiplication = new BinaryExpression(addition, new Token(Token.TokenType.ASTERISK, "*"), new IntegerLiteral(2));

        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = multiplication.accept(visitor);
        Assertions.assertEquals(16, result);
    }

    @Test
    public void testAbsExpressionEvaluation() {
        Expression expression = new AbsExpression(new IntegerLiteral(-10));
        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(10, result);
    }

    @Test
    public void testIdentifierExpressionEvaluation() {
        Environment env = new Environment();
        env.define("x", 5);
        env.define("y", 3);

        Expression expression = new BinaryExpression(
                new IdentifierExpression("x"),
                new Token(Token.TokenType.PLUS, "+"),
                new IdentifierExpression("y"));

        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(8, result);
    }

    @Test
    public void testUndefinedIdentifier() {
        Environment env = new Environment();
        Expression expression = new IdentifierExpression("x");
        EvaluationVisitor visitor = new EvaluationVisitor(env);

        Assertions.assertThrows(RuntimeException.class, () -> expression.accept(visitor));
    }

    @Test
    public void testComplexExpressionEvaluation() {
        Environment env = new Environment();
        env.define("x", 10);

        Expression expression = new BinaryExpression(
                new AbsExpression(
                        new BinaryExpression(
                                new IntegerLiteral(5),
                                new Token(Token.TokenType.MINUS, "-"),
                                new IdentifierExpression("x"))),
                new Token(Token.TokenType.ASTERISK, "*"),
                new BinaryExpression(
                        new IdentifierExpression("x"),
                        new Token(Token.TokenType.SLASH, "/"),
                        new IntegerLiteral(2)));

        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(25, result);
    }

    @Test
    public void testNegativeIntegerLiteralEvaluation() {
        Expression expression = new IntegerLiteral(-7);
        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(-7, result);
    }

    @Test
    public void testAbsExpressionWithIdentifier() {
        Environment env = new Environment();
        env.define("x", -5);

        Expression expression = new AbsExpression(new IdentifierExpression("x"));
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(5, result);
    }

    @Test
    public void testBinaryExpressionWithMultipleOperators() {
        Expression expression = new BinaryExpression(
                new IntegerLiteral(2),
                new Token(Token.TokenType.PLUS, "+"),
                new BinaryExpression(
                        new IntegerLiteral(3),
                        new Token(Token.TokenType.ASTERISK, "*"),
                        new IntegerLiteral(4)));

        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);
        int result = expression.accept(visitor);
        Assertions.assertEquals(14, result);
    }

    @Test
    public void testIllegalOperator() {
        Expression expression = new BinaryExpression(
                new IntegerLiteral(2),
                new Token(Token.TokenType.ILLEGAL, "%"),
                new IntegerLiteral(3));

        Environment env = new Environment();
        EvaluationVisitor visitor = new EvaluationVisitor(env);

        Assertions.assertThrows(IllegalArgumentException.class, () -> expression.accept(visitor));
    }
}