package com.ingtech.encoder.rotor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rotor implements Prepare, CharEncode {

    private final int ALPHABET_SIZE = 26;

    private int indexOffset = 0;
    private final List<String> indexList;
    private final List<String> referenceList;

    public Rotor(List<String> index, List<String> reference) {
        indexList = new ArrayList<>(index);
        referenceList = new ArrayList<>(reference);
    }

    @Override
    public void setup(String key) {
        for (int i = 0; i < indexList.size(); i++) {
            if (key.equals(indexList.get(i))) {
                indexOffset = i;
                break;
            }
        }
    }

    public int leftEncode(int charIndex) {
        int encodedCharIndex = charIndex;
        for (int i = indexOffset; i < indexOffset + ALPHABET_SIZE; i++) {
            if (referenceList.get(charIndex + indexOffset).equals(indexList.get(i))) {
                encodedCharIndex = i - indexOffset;
                break;
            }
        }

        return encodedCharIndex;
    }

    public int rightEncode(int charIndex) {
        int encodedCharIndex = charIndex;
        for (int i = indexOffset; i < indexOffset + ALPHABET_SIZE; i++) {
            if (indexList.get(charIndex + indexOffset).equals(referenceList.get(i))) {
                encodedCharIndex = i - indexOffset;
                break;
            }
        }

        return encodedCharIndex;
    }
}
