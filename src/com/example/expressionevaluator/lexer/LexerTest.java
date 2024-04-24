package com.example.expressionevaluator.lexer;

import com.example.expressionevaluator.token.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {

    @Test
    public void testIdentifiers() {
        String input = "abc def abs";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abc"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "def"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ABS, "abs"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testNumbers() {
        String input = "123 456 0";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.INT, "123"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.INT, "456"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.INT, "0"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testOperators() {
        String input = "+ - * / = , ;";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.PLUS, "+"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.MINUS, "-"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ASTERISK, "*"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.SLASH, "/"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ASSIGN, "="), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.COMMA, ","), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.SEMICOLON, ";"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testParentheses() {
        String input = "( )";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.LPAREN, "("), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.RPAREN, ")"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testIllegalCharacters() {
        String input = "@ # $";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "@"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "#"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "$"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testEOF() {
        String input = "";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testWhitespace() {
        String input = "abc\tdef\n\r ghi";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abc"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "def"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "ghi"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testMixedTokens() {
        String input = "abc 123 + - * / ( ) =";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abc"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.INT, "123"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.PLUS, "+"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.MINUS, "-"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ASTERISK, "*"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.SLASH, "/"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.LPAREN, "("), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.RPAREN, ")"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ASSIGN, "="), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testConsecutiveIllegalCharacters() {
        String input = "abc@#$def";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abc"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "@"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "#"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.ILLEGAL, "$"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "def"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testReservedKeywords() {
        String input = "abs abs_var abs123";
        Lexer lexer = new Lexer(input);

        Assertions.assertEquals(new Token(Token.TokenType.ABS, "abs"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abs_var"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.IDENT, "abs123"), lexer.nextToken());
        Assertions.assertEquals(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    @Test
    public void testInvalidIdentifiersAndNumbers() {
        String input = "abc123+ 45.67&89 -123abc";
        Lexer lexer = new Lexer(input);

        checkToken(new Token(Token.TokenType.IDENT, "abc123"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.PLUS, "+"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.INT, "45"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.ILLEGAL, "."), lexer.nextToken());
        checkToken(new Token(Token.TokenType.INT, "67"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.ILLEGAL, "&"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.INT, "89"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.MINUS, "-"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.INT, "123"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.IDENT, "abc"), lexer.nextToken());
        checkToken(new Token(Token.TokenType.EOF, ""), lexer.nextToken());
    }

    private void checkToken(Token expected, Token actual) {
        System.out.println("Expected: " + expected + " Actual: " + actual);
        assertEquals(expected, actual);
    }
}