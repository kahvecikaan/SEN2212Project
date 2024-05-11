package com.example.expressionevaluator.lexer;

import com.example.expressionevaluator.token.Token;
public class Lexer {
    private final String input;
    private int position;
    private int readPosition;
    private char currentChar;

    public Lexer(String input) {
        this.input = input;
        readChar(); // Initialize currentChar to the first character of the input
    }
    public Token peek() {
        // Save the current state
        int tempPosition = position;
        int tempReadPosition = readPosition;
        char tempCurrentChar = currentChar;

        Token nextToken = nextToken();

        // Restore the state
        position = tempPosition;
        readPosition = tempReadPosition;
        currentChar = tempCurrentChar;

        return nextToken;
    }
    private void readChar() {
        if (readPosition >= input.length()) {
            currentChar = 0; // EOF, represented as null character
        } else {
            currentChar = input.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }
    public Token nextToken() {
        Token token = null;
        skipWhitespace();

        if (currentChar == 0) {
            token = new Token(Token.TokenType.EOF, "");
            return token;
        }

        if (Character.isDigit(currentChar)) {
            token = new Token(Token.TokenType.INT, readNumber());
        } else if (Character.isLetter(currentChar) || currentChar == '_') {
            String ident = readIdentifier();
            token = new Token(Token.lookupIdent(ident), ident);
        } else {
            switch (currentChar) {
                case '+':
                    token = new Token(Token.TokenType.PLUS, Character.toString(currentChar));
                    readChar();
                    break;
                case '-':
                    token = new Token(Token.TokenType.MINUS, Character.toString(currentChar));
                    readChar();
                    break;
                case '*':
                    token = new Token(Token.TokenType.ASTERISK, Character.toString(currentChar));
                    readChar();
                    break;
                case '/':
                    token = new Token(Token.TokenType.SLASH, Character.toString(currentChar));
                    readChar();
                    break;
                case '(':
                    token = new Token(Token.TokenType.LPAREN, Character.toString(currentChar));
                    readChar();
                    break;
                case ')':
                    token = new Token(Token.TokenType.RPAREN, Character.toString(currentChar));
                    readChar();
                    break;
                case '=':
                    token = new Token(Token.TokenType.ASSIGN, Character.toString(currentChar));
                    readChar();
                    break;
                case ',':
                    token = new Token(Token.TokenType.COMMA, Character.toString(currentChar));
                    readChar();
                    break;
                case ';':
                    token = new Token(Token.TokenType.SEMICOLON, Character.toString(currentChar));
                    readChar();
                    break;
                default:
                    token = new Token(Token.TokenType.ILLEGAL, Character.toString(currentChar));
                    readChar();
                    break;
            }
        }

        return token;
    }
    private void skipWhitespace() {
        while (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r') {
            readChar();
        }
    }
    private String readIdentifier() {
        int startPosition = position;
        if (!Character.isLetter(currentChar)) {
            return Character.toString(currentChar);
        }
        readChar();
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            readChar();
        }
        return input.substring(startPosition, position);
    }
    private String readNumber() {
        int startPosition = position;
        while (Character.isDigit(currentChar)) {
            readChar();
        }
        return input.substring(startPosition, position);
    }
}
