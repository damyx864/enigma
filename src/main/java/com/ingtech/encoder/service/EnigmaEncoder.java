package com.ingtech.encoder.service;

import com.ingtech.conf.EnigmaConfiguration;
import com.ingtech.encoder.rotor.Reflector;
import com.ingtech.encoder.rotor.Rotor1;
import com.ingtech.encoder.rotor.Rotor2;
import com.ingtech.encoder.rotor.Rotor3;
import org.springframework.stereotype.Service;

@Service
public class EnigmaEncoder implements EncodeMessage {

    private static final String NOT_LETTER = "[^a-zA-Z]";
    private static final String BIG_LETTER = "[A-Z]";

    private final EnigmaConfiguration enigmaConfiguration;

    private final Rotor1 rotor1;
    private final Rotor2 rotor2;
    private final Rotor3 rotor3;
    private final Reflector reflector;

    public EnigmaEncoder(EnigmaConfiguration enigmaConfiguration) {
        // set the main configuration
        this.enigmaConfiguration = enigmaConfiguration;
        // Initialize rotors from configuration
        this.rotor1 = new Rotor1(enigmaConfiguration.rotorIndex(), enigmaConfiguration.rotor1Reference());
        this.rotor2 = new Rotor2(enigmaConfiguration.rotorIndex(), enigmaConfiguration.rotor2Reference());
        this.rotor3 = new Rotor3(enigmaConfiguration.rotorIndex(), enigmaConfiguration.rotor3Reference());
        this.reflector = new Reflector(enigmaConfiguration.reflectorReference());
    }

    @Override
    public String encodeMessage(Message msg) {

        StringBuilder encryptedMessage;

        if (msg.key() == null || msg.key().length() != enigmaConfiguration.rotorCount())
            return msg.message();
        else if (msg.key().toUpperCase().matches(enigmaConfiguration.keyPattern())
                && msg.message() != null && !msg.message().isEmpty()) {

            // Set up the initial position
            rotor1.setup(msg.key().substring(0,1).toUpperCase());
            rotor2.setup(msg.key().substring(1,2).toUpperCase());
            rotor3.setup(msg.key().substring(2,3).toUpperCase());

            encryptedMessage = new StringBuilder();

            for (char letter: msg.message().toCharArray()) {
                String input = String.valueOf(letter);

                // will not encrypt outside [a <--> z] or [A <--> Z]
                if (input.matches(NOT_LETTER)) {
                    encryptedMessage.append(letter);
                }
                else {
                    // to preserve the input case
                    boolean isUpperCase = input.matches(BIG_LETTER);

                    // Encoding engine
                    String output =  enigmaConfiguration.alphabet().get(
                            rotor1.rightEncode(
                            rotor2.rightEncode(
                            rotor3.rightEncode(
                            reflector.encode(
                            rotor3.leftEncode(
                            rotor2.leftEncode(
                            rotor1.leftEncode(
                            enigmaConfiguration.alphabet()
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
            return msg.message();
    }
}
