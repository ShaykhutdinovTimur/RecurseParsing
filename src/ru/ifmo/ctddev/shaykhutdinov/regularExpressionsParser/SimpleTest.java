package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */
public class SimpleTest {
    private Parser p;

    public static void main(String[] args) throws IOException, ParseException {
        new SimpleTest().run();
    }

    private void run() throws ParseException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        p = new Parser();
        Tree t = p.parse(inputStream);
        p = new Parser();//breakpoint
    }
}
