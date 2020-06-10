package com.ingtech.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource(value="classpath:enigma.properties")
public class EnigmaConfiguration {

    private final int ALPHABET_SIZE = 26;

    @Value("${rotor1Reference}")
    private String[] rotor1Reference = new String[ALPHABET_SIZE * 2];

    @Value("${rotor2Reference}")
    private String[] rotor2Reference = new String[ALPHABET_SIZE * 2];

    @Value("${rotor3Reference}")
    private String[] rotor3Reference = new String[ALPHABET_SIZE * 2];

    @Value("${rotorIndex}")
    private String[] rotorIndex      = new String[ALPHABET_SIZE * 2];

    @Value("${alphabet}")
    private List<String> alphabet    = new ArrayList<>();

    public String[] getRotor1Reference() {
        return rotor1Reference;
    }

    public String[] getRotor2Reference() {
        return rotor2Reference;
    }

    public String[] getRotor3Reference() {
        return rotor3Reference;
    }

    public String[] getRotorIndex() {
        return rotorIndex;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    private EnigmaConfiguration() {
    }
}
