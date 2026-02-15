package com.ingtech.encoder.rotor;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Rotor implements Prepare, CharEncode {

    private final int ALPHABET_SIZE = 26;

    private int indexOffset = 0;
    private final List<String> index;
    private final List<String> reference;

    public Rotor(List<String> index, List<String> reference) {
        this.index = index;
        this.reference = reference;
    }

    @Override
    public void setup(String key) {
        OptionalInt offset = IntStream.range(0, index.size())
                .filter(i -> key.equals(index.get(i)))
                .findFirst();

        if (offset.isPresent()) {
            indexOffset = offset.getAsInt();
        }
    }

    public int leftEncode(int charIndex) {
        int encodedCharIndex = charIndex;

        OptionalInt result = IntStream.range(indexOffset, indexOffset + ALPHABET_SIZE)
                .filter(i -> reference.get(charIndex + indexOffset).equals(index.get(i)))
                .findFirst();

        if (result.isPresent()) {
            encodedCharIndex = result.getAsInt() - indexOffset;
        }

        return encodedCharIndex;
    }

    public int rightEncode(int charIndex) {
        int encodedCharIndex = charIndex;

        OptionalInt result = IntStream.range(indexOffset, indexOffset + ALPHABET_SIZE)
                .filter(i -> index.get(charIndex + indexOffset).equals(reference.get(i)))
                .findFirst();

        if (result.isPresent()) {
            encodedCharIndex = result.getAsInt() - indexOffset;
        }

        return encodedCharIndex;
    }
}
