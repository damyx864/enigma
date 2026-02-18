package com.ingtech.controller;

import com.ingtech.encoder.service.EnigmaEncoder;
import com.ingtech.encoder.service.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enigma")
public class EnigmaController {

    private final EnigmaEncoder enigmaEncoder;

    public EnigmaController(EnigmaEncoder enigmaEncoder) {
        this.enigmaEncoder = enigmaEncoder;
    }

    @PostMapping(path = "/encode",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message encode(@RequestBody Message incommingMessage) {
        return new Message("", enigmaEncoder.encodeMessage(incommingMessage));
    }
}
