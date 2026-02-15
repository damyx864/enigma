package com.ingtech.encoder.rotor;

import java.util.List;

public class Rotor1 extends Rotor {

    private char spinningKey;

    public Rotor1(List<String> index, List<String>  reference) {
        super(index, reference);
    }

    @Override
    public void setup(String key) {
        super.setup(key);
        spinningKey = key.charAt(0);
    }

    @Override
    public int leftEncode(int charIndex) {

        // implement Rotor 1 (or rightmost)
        // dynamic behavior
        spinningKey += 1;

        setup(String.valueOf(spinningKey));

        return super.leftEncode(charIndex);
    }

}
