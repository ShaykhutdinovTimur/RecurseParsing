package ru.ifmo.ctddev.shaykhutdinov.regularExpressionsParser;

/**
 * Created by Shaykhutdinov Timur on 23.03.16.
 */

public enum Token {
    OPEN_BRACKET, CLOSE_BRACKET, KLEENE_CLOSURE, CHOOSE, CHARACTER, END;

    private int value;

    public int getValue() {
        // always uses after value set
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
