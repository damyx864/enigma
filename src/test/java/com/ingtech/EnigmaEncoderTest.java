package com.ingtech;

import com.ingtech.encoder.*;
import com.ingtech.util.EnigmaConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

@WebMvcTest
@ComponentScan(basePackages = "com.ingtech")
public class EnigmaEncoderTest {

    @Autowired
    Reflector reflector;

    @Autowired
    EnigmaConfiguration enigmaConfiguration;

    @Autowired
    EnigmaEncoder enigmaEncoder;

    @ParameterizedTest
    @CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
    public void testEnigmaEncoder(String key, String message, String empty, String expected) {

        enigmaEncoder.setReflector(reflector);

        enigmaEncoder.setRotor1(new Rotor1(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor1Reference()));
        enigmaEncoder.setRotor2(new Rotor2(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor2Reference()));
        enigmaEncoder.setRotor3(new Rotor3(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor3Reference()));

        Assertions.assertEquals(expected, enigmaEncoder.encode(key, message));
        Assertions.assertEquals(empty, null);
    }

}
