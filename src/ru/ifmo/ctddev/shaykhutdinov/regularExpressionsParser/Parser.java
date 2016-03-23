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
                lex.nextToken();
                Tree sub = S();
                if (lex.curToken() != Token.CLOSE_BRACKET) {
                    throw new ParseException(") expected at position ", lex.curPos());
                }
                lex.nextToken();
                Tree cont = SPrime();
                return new Tree("S", new Tree("("), sub, new Tree(")"), cont);
            case CLOSE_BRACKET:
            case END:
                return new Tree("S");
            default:
                throw new AssertionError();
        }
    }

    private Tree SPrime() throws ParseException {
        switch (lex.curToken()) {
            case OPEN_BRACKET:
                Tree sub = S();
                Tree cont = SPrime();
                return new Tree("S'", sub, cont);
            case CLOSE_BRACKET:
            case END:
                return new Tree("S'");
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
