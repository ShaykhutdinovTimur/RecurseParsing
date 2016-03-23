package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

import org.StructureGraphic.v1.DSutils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */
public class SimpleTest {

    public static void main(String[] args) throws IOException, ParseException {
        new SimpleTest().run();
    }

    private void run() throws ParseException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Tree t = (new Parser()).parse(inputStream);
        DSutils.show(t, 18, 12);
    }
}
