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
                return new Tree("S", arg);
            case CLOSE_BRACKET:
            case END:
                return new Tree("S");
            default:
                throw new AssertionError();
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
            case CLOSE_BRACKET:
            case END:
                return new Tree("S'");
            default:
                throw new AssertionError();
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
                Tree cont = Scont();
                return new Tree("C", new Tree("("), sub, new Tree(")"), cont);
            case CHARACTER:
                String val = String.valueOf((char) lex.curToken().getValue());
                lex.nextToken();
                Tree s = Scont();
                return new Tree("C", new Tree(val), s);
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
