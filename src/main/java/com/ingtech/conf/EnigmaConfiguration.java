package com.ingtech.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "enigma")
public record EnigmaConfiguration(
        String keyPattern,
        int rotorCount,
        int alphabetSize,
        List<String> rotor1Reference,
        List<String> rotor2Reference,
        List<String> rotor3Reference,
        List<String> rotorIndex,
        List<String> alphabet,
        List<String> reflectorReference
) {
}
