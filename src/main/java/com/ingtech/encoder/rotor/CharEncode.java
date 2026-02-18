package com.ingtech.encoder.rotor;

public interface CharEncode {

    default int leftEncode(int charIndex) {
        return charIndex;
    }

    default int rightEncode(int charIndex) {
        return charIndex;
    }

    default int encode(int charIndex) {
        return charIndex;
    }
}
