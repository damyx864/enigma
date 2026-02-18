package com.ingtech.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "enigma")
public record EnigmaConfiguration(
        String keyPattern,
        int rotorCount,
        int alphabetSize,
        String[] rotor1Reference,
        String[] rotor2Reference,
        String[] rotor3Reference,
        String[] rotorIndex,
        List<String> alphabet,
        List<String> reflectorReference
) {
}
