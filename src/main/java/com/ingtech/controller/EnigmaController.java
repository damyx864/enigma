package com.ingtech.controller;

import com.ingtech.encoder.*;
import com.ingtech.util.EnigmaConfiguration;
import com.ingtech.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enigma")
public class EnigmaController {

    @Autowired
    Reflector reflector;

    @Autowired
    EnigmaConfiguration enigmaConfiguration;

    @Autowired
    EnigmaEncoder enigmaEncoder;

    @PostMapping(path = "/encode", consumes = "application/json", produces = "application/json")
    public Message encode(@RequestBody Message message) {

        enigmaEncoder.setReflector(reflector);

        enigmaEncoder.setRotor1(new Rotor1(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor1Reference()));
        enigmaEncoder.setRotor2(new Rotor2(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor2Reference()));
        enigmaEncoder.setRotor3(new Rotor3(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor3Reference()));

        var encryptedMessage = new Message("", enigmaEncoder.encode(message.getKey(), message.getMessage()));

        return encryptedMessage;
    }

}
