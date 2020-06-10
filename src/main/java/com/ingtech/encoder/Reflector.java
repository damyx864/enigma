package com.ingtech.encoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(value="classpath:enigma.properties")
public class Reflector {

    @Value("${reflector}")
    private List<String> reference;

    private Reflector() {}

    public int encode(int charIndex) {

        var seekedChar = reference.get(charIndex);

        int index = reference.indexOf(seekedChar);

        if (index == charIndex) {
            index += 1 + reference.subList(index + 1, reference.size()).indexOf(seekedChar);
        }

        return index;
    }
}
