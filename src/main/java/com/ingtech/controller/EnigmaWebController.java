package com.ingtech.controller;

import com.ingtech.encoder.*;
import com.ingtech.util.EnigmaConfiguration;
import com.ingtech.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enigma")
public class EnigmaWebController {
    @Autowired
    Reflector reflector;

    @Autowired
    EnigmaConfiguration enigmaConfiguration;

    @Autowired
    EnigmaEncoder enigmaEncoder;


    @PostMapping("/encrypt")
    public String encryptOrDecrypt(@ModelAttribute Message message, Model model) {

        enigmaEncoder.setReflector(reflector);

        enigmaEncoder.setRotor1(new Rotor1(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor1Reference()));
        enigmaEncoder.setRotor2(new Rotor2(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor2Reference()));
        enigmaEncoder.setRotor3(new Rotor3(enigmaConfiguration.getRotorIndex(), enigmaConfiguration.getRotor3Reference()));

        var encryptedMessage = new Message("", enigmaEncoder.encode(message.getKey(), message.getMessage()));

        model.addAttribute("msg", encryptedMessage);

        return "editor";
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("msg", new Message("", ""));
        return "index";
    }
}
