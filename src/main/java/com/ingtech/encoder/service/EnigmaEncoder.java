package com.ingtech.encoder.service;

import com.ingtech.conf.EnigmaConfiguration;
import com.ingtech.encoder.rotor.Reflector;
import com.ingtech.encoder.rotor.Rotor1;
import com.ingtech.encoder.rotor.Rotor2;
import com.ingtech.encoder.rotor.Rotor3;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EnigmaEncoder implements EncodeMessage {

    private static final Pattern LETTER = Pattern.compile("[a-zA-Z]");
    private static final Pattern BIG_LETTER = Pattern.compile("[A-Z]");
    private static final Predicate<String> isLetter = LETTER.asPredicate();
    private static final Predicate<String> isBigLetter = BIG_LETTER.asPredicate();

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
    public String encodeMessage(Message message) {

        Predicate<Message> hasValidKey = m ->
                m.key() != null
                        && m.key().length() == enigmaConfiguration.rotorCount()
                        && m.key().toUpperCase().matches(enigmaConfiguration.keyPattern());

        Predicate<Message> hasValidMessage = m ->
                m.message() != null
                        && !m.message().isEmpty();

        // Validate the key and message, should be done externally
        if (!hasValidKey.test(message) || !hasValidMessage.test(message)) {
            return message.message();
        }

        // Get the initialized engine, once per message
        IntUnaryOperator encodingEngine = initializeRotorsAndEncodingEngine(message);
        // Function to apply the encoding
        Function<String, String> encodeIfLetter = input -> isLetter.test(input) ? encodeLetter(input, encodingEngine) : input;

        return Optional.of(message)
                .map(msg -> msg.message().chars()
                        .mapToObj(codePoint -> String.valueOf((char) codePoint))
                        .map(encodeIfLetter)
                        .collect(Collectors.joining()))
                .orElseGet(message::message);
    }

    private String encodeLetter(String input, IntUnaryOperator encodingEngine) {
        // Find the matching index in the alphabet after encoding,
        // which is the core feature of the encryption
        boolean isUpperCase = isBigLetter.test(input);
        int inputIndex = enigmaConfiguration.alphabet().indexOf(input.toUpperCase());
        int outputIndex = encodingEngine.applyAsInt(inputIndex);
        String output = enigmaConfiguration.alphabet().get(outputIndex);
        return isUpperCase ? output : output.toLowerCase();
    }

    @Nonnull
    private IntUnaryOperator initializeRotorsAndEncodingEngine(Message message) {
        // Set up the initial position of each rotor
        rotor1.setup(message.key().substring(0, 1).toUpperCase());
        rotor2.setup(message.key().substring(1, 2).toUpperCase());
        rotor3.setup(message.key().substring(2, 3).toUpperCase());

        // Encoding engine as a composed function
        return ((IntUnaryOperator) rotor1::leftEncode)
                .andThen(rotor2::leftEncode)
                .andThen(rotor3::leftEncode)
                .andThen(reflector::encode)
                .andThen(rotor3::rightEncode)
                .andThen(rotor2::rightEncode)
                .andThen(rotor1::rightEncode);
    }
}
