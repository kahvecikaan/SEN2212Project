package com.example.expressionevaluator.lexer;

import com.example.expressionevaluator.token.Token;
public class Lexer {
    private final String input;
    private int position;
    private int readPosition;
    private int currentChar;

    public Lexer(String input) {
        this.input = input;
        readChar();
    }

    private void readChar() {
        if(readPosition >= input.length()) {
            currentChar = 0; // represents EOF
        } else {
            currentChar = input.charAt(readPosition);
        }

        position = readPosition;
        readPosition++;
    }

    public Token nextToken() {
        skipWhitespace();

        Token token;
        switch(currentChar) {
            case '+':
                token = new Token(Token.TokenType.PLUS, "+");
                readChar();
                break;
            case '-':
                token = new Token(Token.TokenType.MINUS, "-");
                readChar();
                break;
            case '/':
                token = new Token(Token.TokenType.SLASH, "/");
                readChar();
                break;
            case '*':
                token = new Token(Token.TokenType.ASTERISK, "*");
                readChar();
                break;
            case '(':
                token = new Token(Token.TokenType.LPAREN, "(");
                readChar();
                break;
            case ')':
                token = new Token(Token.TokenType.RPAREN, ")");
                readChar();
                break;
            case '=':
                token = new Token(Token.TokenType.ASSIGN, "=");
                readChar();
                break;
            case ',':
                token = new Token(Token.TokenType.COMMA, ",");
                readChar();
                break;
            case ';':
                token = new Token(Token.TokenType.SEMICOLON, ";");
                readChar();
                break;
            case 0:
                token = new Token(Token.TokenType.EOF, "");
                break;
            default:
                if(Character.isLetter(currentChar)) {
                    String literal = readIdentifier();
                    token = new Token(Token.lookupIdent(literal), literal);
                }
                else if(Character.isDigit(currentChar)) {
                    String literal = readNumber();
                    token = new Token(Token.TokenType.INT, literal);
                    if(Character.isLetter(currentChar)) {
                        return token;
                    }
                }
                else {
                    token = new Token(Token.TokenType.ILLEGAL, String.valueOf((char)currentChar));
                    readChar(); // move past the illegal character
                }

                return token;
        }

        return token;
    }

    private void skipWhitespace() {
        while(currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r') {
            readChar();
        }
    }

    private String readIdentifier() {
        int start = position;
        while(isValidIdentifierChar(currentChar)) {
            readChar();
        }

        return input.substring(start, position);
    }

    private String readNumber() {
        int start = position;

        while(Character.isDigit(currentChar)) {
            readChar();
        }

        return input.substring(start, position);
    }

    private boolean isValidTokenChar(int c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')'
                || c == '=' || c == ',' || c == ';' || Character.isLetter(c) || Character.isDigit(c);
    }

    private boolean isValidIdentifierChar(int c) {
        return Character.isLetter(c) || c == '_' || (position > 0 && Character.isDigit(c));
    }
}
