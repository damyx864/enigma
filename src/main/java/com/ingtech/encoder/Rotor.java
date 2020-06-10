package com.ingtech.encoder;

import java.util.Arrays;

public class Rotor implements Prepare {

    private final int ALPHABET_SIZE = 26;

    private int indexOffset = 0;
    private String[][] data;

    public Rotor(String[] index, String[] reference) {
        data = new String[2][reference.length];
        data[0] = Arrays.copyOf(index, index.length);
        data[1] = Arrays.copyOf(reference, reference.length);
    }

    @Override
    public void setup(String key) {

        for (int i = 0; i < data[0].length; i++) {
            if (key.equals(data[0][i])) {
                indexOffset = i;
                break;
            }
        }
    }

    public int leftEncode(int charIndex) {
         int encodedCharIndex = charIndex;
        for (int i = indexOffset; i < indexOffset + ALPHABET_SIZE; i++) {
            if (data[1][charIndex + indexOffset].equals(data[0][i])) {
                encodedCharIndex = i - indexOffset;
                break;
            }
        }

        return encodedCharIndex;
    }

    public int rightEncode(int charIndex) {
        int encodedCharIndex = charIndex;
        for (int i = indexOffset; i < indexOffset + ALPHABET_SIZE; i++) {
            if (data[0][charIndex + indexOffset].equals(data[1][i])) {
                encodedCharIndex = i - indexOffset;
                break;
            }
        }

        return encodedCharIndex;
    }
}
