package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.Expression;
import com.example.expressionevaluator.env.Environment;
import com.example.expressionevaluator.evaluator.EvaluationVisitor;
import com.example.expressionevaluator.lexer.Lexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrattParserTest {
    private EvaluationVisitor evaluator;
    private Environment environment;

    @BeforeEach
    void setUp() {
        environment = new Environment();
        evaluator = new EvaluationVisitor(environment);
    }

    private int evaluateExpression(String input, Environment env) {
        Lexer lexer = new Lexer(input);
        PrattParser parser = new PrattParser(lexer, env);
        Expression expression = parser.parse();
        return expression.accept(evaluator);
    }

    @Test
    void testSimpleArithmetic() {
        assertEquals(17, evaluateExpression("2 + 3 * 5;", environment));
    }

    @Test
    void testParenthesesHandling() {
        assertEquals(25, evaluateExpression("(2 + 3) * 5;", environment));
    }

    @Test
    void testVariableAssignmentAndUse() {
        evaluateExpression("x = 10;", environment);
        assertEquals(20, evaluateExpression("x * 2;", environment));
    }

    @Test
    void testNestedParentheses() {
        assertEquals(35, evaluateExpression("((2 + 3) * (3 + 4));", environment));
    }

    @Test
    void testDivisionByZero() {
        Exception exception = assertThrows(RuntimeException.class, () -> evaluateExpression("10 / 0;", environment));
        assertTrue(exception.getMessage().contains("Division by zero"));
    }

    @Test
    void testSequentialAssignments() {
        evaluateExpression("x = 5;", environment);
        evaluateExpression("y = x + 3;", environment);
        assertEquals(16, evaluateExpression("y * 2;", environment));
    }

    @Test
    void testVariableReassignment() {
        evaluateExpression("x = 10;", environment);
        evaluateExpression("x = 20;", environment);
        assertEquals(40, evaluateExpression("x * 2;", environment));
    }

    @Test
    void testUndefinedVariable() {
        Exception exception = assertThrows(RuntimeException.class, () -> evaluateExpression("x * 2;", environment));
        assertTrue(exception.getMessage().contains("Undefined variable"));
    }

    @Test
    void testMultipleExpressions() {
        evaluateExpression("x = 5;", environment);
        evaluateExpression("y = 10;", environment);
        assertEquals(15, evaluateExpression("x + y;", environment));
    }

    @Test
    void testSequentialVariableAccess() {
        evaluateExpression("x = 10;", environment);
        int resultAfterAssignment = evaluateExpression("x;", environment); // Should return 10
        assertEquals(10, resultAfterAssignment);

        evaluateExpression("x = 20;", environment);
        int resultAfterReassignment = evaluateExpression("x;", environment); // Should return 20
        assertEquals(20, resultAfterReassignment);
    }

}
