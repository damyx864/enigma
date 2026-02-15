package com.ingtech.encoder.rotor;

import java.util.List;

public class Reflector implements CharEncode {

    private final List<String> reference;

    public Reflector(List<String> reference) {
        this.reference = reference;
    }

    @Override
    public int encode(int charIndex) {

        var soughtChar = reference.get(charIndex);
        int index = reference.indexOf(soughtChar);

        if (index == charIndex) {
            index += 1 + reference.subList(index + 1, reference.size()).indexOf(soughtChar);
        }

        return index;
    }
}
