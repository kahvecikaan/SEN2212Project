package com.example.expressionevaluator.token;

import java.util.Objects;

public class Token {
    public enum TokenType {
        ILLEGAL, EOF,
        IDENT, INT,
        ASSIGN, PLUS, MINUS, ASTERISK, SLASH, LPAREN, RPAREN, ABS,
        COMMA, SEMICOLON
    }

    public TokenType type;
    public String literal;

    public Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    public static TokenType lookupIdent(String ident) {
        if(ident.equals("abs")) {
            return TokenType.ABS;
        }
        else {
            return TokenType.IDENT;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type && Objects.equals(literal, token.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, literal);
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", literal='" + literal + '\'' +
                '}';
    }

}
