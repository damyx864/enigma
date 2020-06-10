package com.ingtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingtech.controller.EnigmaController;
import com.ingtech.util.Message;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnigmaController.class)
@ComponentScan(basePackages = "com.ingtech")
public class EnigmaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    // 1 Test 2 Rule Them All

    @ParameterizedTest
    @CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
    public void testEncoder(String key, String inputMessage, String outputKey, String outputMessage) throws Exception {

        var inputMsg = new Message(key, inputMessage);
        var outputMsg = new Message(outputKey, outputMessage);


        mvc.perform(post("/enigma/encode")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputMsg)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(outputMsg.getMessage()));
    }
}
