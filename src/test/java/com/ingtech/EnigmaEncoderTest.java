package com.ingtech;

import com.ingtech.conf.EnigmaConfiguration;
import com.ingtech.encoder.service.EnigmaEncoder;
import com.ingtech.encoder.service.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;

@WebMvcTest
@ComponentScan(basePackages = "com.ingtech")
@EnableConfigurationProperties(EnigmaConfiguration.class)
public class EnigmaEncoderTest {

    @Autowired
    EnigmaEncoder enigmaEncoder;

    @ParameterizedTest
    @CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
    public void testEnigmaEncoder(String key, String message, String emptyKey, String expected) {

        Assertions.assertEquals(expected, enigmaEncoder.encodeMessage(new Message(key, message)));
        Assertions.assertNull(emptyKey);
    }
}
