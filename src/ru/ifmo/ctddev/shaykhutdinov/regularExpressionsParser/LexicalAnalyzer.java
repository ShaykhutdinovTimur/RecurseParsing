package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */
public class LexicalAnalyzer {
    private InputStream is;
    private int curChar, curPos;
    private Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.OPEN_BRACKET;
                break;
            case ')':
                nextChar();
                curToken = Token.CLOSE_BRACKET;
                break;
            case '*':
                nextChar();
                curToken = Token.KLEENE_CLOSURE;
                break;
            case '|':
                nextChar();
                curToken = Token.CHOOSE;
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                if (Character.isLetter(curChar)) {
                    curToken = Token.CHARACTER;
                    curToken.setValue(curChar);
                    nextChar();
                    break;
                } else {
                    throw new ParseException("Illegal character " + (char) curChar, curPos);
                }
        }
    }
}
