package com.ingtech.controller;

import com.ingtech.encoder.service.EnigmaEncoder;
import com.ingtech.encoder.service.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enigma")
public class EnigmaWebController {
    private final EnigmaEncoder enigmaEncoder;

    public EnigmaWebController(EnigmaEncoder enigmaEncoder) {
        this.enigmaEncoder = enigmaEncoder;
    }

    @PostMapping("/encrypt")
    public String encryptOrDecrypt(@ModelAttribute Message incommingMessage, Model model) {

        var encryptedMessage = new Message("", enigmaEncoder.encodeMessage(incommingMessage));
        model.addAttribute("msg", encryptedMessage);
        return "editor";
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("msg", new Message("", ""));
        return "index";
    }
}
