package com.example.expressionevaluator.parser;

import com.example.expressionevaluator.ast.*;
import com.example.expressionevaluator.env.Environment;
import com.example.expressionevaluator.lexer.Lexer;
import com.example.expressionevaluator.token.Token;

public class PrattParser {
    private Lexer lexer;
    private Environment environment;
    public Token currentToken;

    public PrattParser(Lexer lexer, Environment environment) {
        this.lexer = lexer;
        this.environment = environment;
        this.currentToken = lexer.nextToken();
    }

    public Token.TokenType getCurrentTokenType() {
        return currentToken.type;
    }

    public Expression parse() {
        Expression expression = parseExpression(0);

        // Loop to handle situations where there might be multiple expressions or operations in a line
        while (currentToken.type != Token.TokenType.EOF && currentToken.type != Token.TokenType.SEMICOLON) {
            if (currentToken.type == Token.TokenType.ASSIGN) {
                // If it's an assignment, parse it specifically
                consume(Token.TokenType.ASSIGN);  // Consume the '=' operator
                Expression value = parseExpression(0);  // Parse the right-hand side of the assignment
                if (expression instanceof IdentifierExpression) {
                    // Ensure that the left-hand side of an assignment is an identifier
                    String identifier = ((IdentifierExpression) expression).getName();
                    expression = new AssignmentExpression(new IdentifierExpression(identifier), value);
                } else {
                    throw new RuntimeException("Invalid left-hand side of assignment");
                }
            } else {
                Token operator = currentToken;
                int precedence = operator.getPrecedence();
                consume(operator.type);

                if (!isAssignmentOperator(operator)) {
                    Expression right = parseExpression(precedence);
                    expression = new BinaryExpression(expression, operator, right);
                } else {
                    throw new RuntimeException("Unexpected assignment operator");
                }
            }
        }

        return expression;
    }

    private boolean isAssignmentOperator(Token operator) {
        return operator.type == Token.TokenType.ASSIGN;
    }

    public Token peek() {
        return lexer.peek();
    }

    public void consume(Token.TokenType type) {
        if(currentToken.type == type) {
            currentToken = lexer.nextToken();
        } else {
            throw new RuntimeException("Expected token " + type + " but found " + currentToken.type);
        }
    }
    public void advanceIfNextIs(Token.TokenType expectedType) {
        Token nextToken = lexer.peek();
        if (nextToken.type == expectedType) {
            currentToken = lexer.nextToken(); // safely advance to next token
        } else {
            throw new RuntimeException("Expected token " + expectedType + " but found " + nextToken.type);
        }
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Expression parseExpression(int precedence) {
        Token token = currentToken;
        Expression left;

        // Check if the current token can start an expression
        if (isPrefixToken(token.type)) {
            PrefixParselet prefix = getPrefixParselet(token.type);
            if (prefix == null) {
                throw new RuntimeException("Could not parse \"" + token.literal + "\".");
            }
            currentToken = lexer.nextToken();  // Move to the next token after parsing prefix
            left = prefix.parse(this, token);
        } else {
            throw new RuntimeException("Unexpected token: \"" + token.literal + "\" where an expression is expected.");
        }

        // Handle infix operations following the initial token
        while (precedence >= lookAheadTokenPrecedence()) {
            token = currentToken;
            InfixParselet infix = getInfixParselet(token.type);
            if (infix == null || precedence > infix.getPrecedence()) break;

            currentToken = lexer.nextToken(); // Move to the next token before parsing infix
            left = infix.parse(this, left, token);
        }

        return left;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }
    private boolean isPrefixToken(Token.TokenType type) {
        return type == Token.TokenType.IDENT || type == Token.TokenType.INT || type == Token.TokenType.LPAREN
                || type == Token.TokenType.RPAREN || type == Token.TokenType.MINUS || type == Token.TokenType.ABS;
    }

    private PrefixParselet getPrefixParselet(Token.TokenType type) {
        switch(type) {
            case IDENT:
                return new IdentifierParselet();
            case INT:
                return new IntegerParselet();
            case LPAREN:
                return new ParenthesesParselet();
            case MINUS:
                return new UnaryOperatorParselet();
            case ABS:
                return new AbsParselet();
            default:
                return null;
        }
    }

    private InfixParselet getInfixParselet(Token.TokenType type) {
        switch(type) {
            case PLUS:
            case MINUS:
            case ASTERISK:
            case SLASH:
                return new BinaryOperatorParselet(type.getPrecedence());
            default:
                return null;
        }
    }

    private int lookAheadTokenPrecedence() {
        Token nextToken = lexer.peek();
        if(nextToken != null) {
            return nextToken.getPrecedence();
        }

        return 0;
    }
}