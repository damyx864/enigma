package com.ingtech.encoder;

import com.ingtech.util.EnigmaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnigmaEncoder implements Encode {

    private final int ROTOR_COUNT = 3;
    private final String PATTERN = "[A-Z]{3}";

    @Autowired
    EnigmaConfiguration enigmaConfiguration;

    private Rotor1 rotor1;
    private Rotor2 rotor2;
    private Rotor3 rotor3;
    private Reflector reflector;

    public void setRotor1(Rotor1 rotor1) {
        this.rotor1 = rotor1;
    }

    public void setRotor2(Rotor2 rotor2) {
        this.rotor2 = rotor2;
    }

    public void setRotor3(Rotor3 rotor3) {
        this.rotor3 = rotor3;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }

    @Override
    public String encode(String key, String message) {

        StringBuilder encryptedMessage;

        if (key == null || key.length() != ROTOR_COUNT)
            return message;
        else if (key.toUpperCase().matches(PATTERN) && message != null && message.length() > 0) {

            // Setup the initial position
            rotor1.setup(key.substring(0,1).toUpperCase());
            rotor2.setup(key.substring(1,2).toUpperCase());
            rotor3.setup(key.substring(2,3).toUpperCase());

            encryptedMessage = new StringBuilder();

            for (char letter: message.toCharArray()) {
                String input = String.valueOf(letter);

                // will not encrypt outside of [A <--> Z] or [a <--> z]
                if (input.matches("[^a-zA-Z]")) {
                    encryptedMessage.append(letter);
                }
                else {
                    // to preserve input case
                    boolean isUpperCase = false;
                    isUpperCase = input.matches("[A-Z]");

                    // Encoding engine
                    String output =  enigmaConfiguration.getAlphabet().get(
                            rotor1.rightEncode(
                            rotor2.rightEncode(
                            rotor3.rightEncode(
                            reflector.encode(
                            rotor3.leftEncode(
                            rotor2.leftEncode(
                            rotor1.leftEncode(
                            enigmaConfiguration.getAlphabet()
                                    .indexOf(input.toUpperCase())))))))));

                    // set the case back after encoding
                    if (!isUpperCase)
                        encryptedMessage.append(output.toLowerCase());
                    else
                        encryptedMessage.append(output);
                }
            }
            return encryptedMessage.toString();
        }
        else
            return message;
    }
}
