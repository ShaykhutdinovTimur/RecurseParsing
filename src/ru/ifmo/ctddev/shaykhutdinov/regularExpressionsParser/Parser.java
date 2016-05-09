package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */
public class Parser {
    private LexicalAnalyzer lex;

    private Tree S() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
                Tree arg = C();
                if (lex.curToken() == Token.END) {
                    return new Tree("S", arg);
                }
                throw new AssertionError();
            case END:
                return new Tree("S");
            case CHOOSE:
                throw new ParseException("| not expected at position ", lex.curPos());
            case PLUS:
                throw new ParseException("+ not expected at position ", lex.curPos());
            case KLEENE_CLOSURE:
                throw new ParseException("* not expected at position ", lex.curPos());
            case CLOSE_BRACKET:
                throw new ParseException(") not expected at position ", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    private Tree B() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
                Tree arg = C();
                return new Tree("B", arg);
            default:
                return new Tree("B");
        }
    }

    private Tree Scont() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
            case CHARACTER:
            case KLEENE_CLOSURE:
            case CHOOSE:
                Tree arg = Ccont();
                return new Tree("S'", arg);
            case END:
                return new Tree("S'");
            default:
                return new Tree("S'");
        }
    }

    private Tree Ccont() throws ParseException {
        Tree cont, after;
        switch (lex.curToken()) {
            case KLEENE_CLOSURE:
                lex.nextToken();
                cont = Scont();
                return new Tree("C'", new Tree("*"), cont);
            case CHOOSE:
                lex.nextToken();
                cont = C();
                after = Scont();
                return new Tree("C'", new Tree("|"), cont, after);
            case OPEN_BRACKET:
            case CHARACTER:
                cont = C();
                after = Scont();
                return new Tree("C'", cont, after);
            case CLOSE_BRACKET:
                throw new ParseException(") not expected at position ", lex.curPos());
            case PLUS:
                throw new ParseException("+ not expected at position ", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    private Tree C() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
                lex.nextToken();
                Tree sub = C();
                if (lex.curToken() != Token.CLOSE_BRACKET) {
                    throw new ParseException(") expected at position ", lex.curPos());
                }
                lex.nextToken();
                if (lex.curToken() == Token.PLUS) {
                    lex.nextToken();
                    Tree cont = B();
                    return new Tree("C", new Tree("("), sub, new Tree(")"), new Tree("+"), cont);
                } else {
                    Tree cont = Scont();
                    return new Tree("C", new Tree("("), sub, new Tree(")"), cont);
                }
            case CHARACTER:
                String val = String.valueOf((char) lex.curToken().getValue());
                lex.nextToken();
                Tree s = Scont();
                return new Tree("C", new Tree(val), s);
            case CHOOSE:
                throw new ParseException("| not expected at position ", lex.curPos());
            case PLUS:
                throw new ParseException("+ not expected at position ", lex.curPos());
            case KLEENE_CLOSURE:
                throw new ParseException("* not expected at position ", lex.curPos());
            case CLOSE_BRACKET:
                throw new ParseException(") not expected at position ", lex.curPos());
            default:
                throw new AssertionError();
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
