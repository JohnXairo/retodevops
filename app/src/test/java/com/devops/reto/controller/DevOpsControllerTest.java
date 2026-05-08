package com.devops.reto.controller;

import com.devops.reto.model.MessageRequest;
import com.devops.reto.security.JwtService;
import com.devops.reto.service.DevOpsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DevOpsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Value("${app.security.api-key}")
    private String apiKey;

    private MessageRequest validRequest() {
        return new MessageRequest("This is a test", "Juan Perez", "Rita Asturia", 45);
    }

    @Test
    void shouldReturn200WithValidRequest() throws Exception {
        mockMvc.perform(post("/DevOps")
                .header("X-Parse-REST-API-Key", apiKey)
                .header("X-JWT-KWY", jwtService.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                .value("Hello Juan Perez your message will be sent"));
    }

    @Test
    void shouldReturn403WithInvalidApiKey() throws Exception {
        mockMvc.perform(post("/DevOps")
                .header("X-Parse-REST-API-Key", "invalid-key")
                .header("X-JWT-KWY", jwtService.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnErrorOnGetMethod() throws Exception {
        mockMvc.perform(get("/DevOps")
                .header("X-Parse-REST-API-Key", apiKey)
                .header("X-JWT-KWY", jwtService.generateToken()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void shouldReturnErrorOnPutMethod() throws Exception {
        mockMvc.perform(put("/DevOps")
                .header("X-Parse-REST-API-Key", apiKey)
                .header("X-JWT-KWY", jwtService.generateToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("ERROR"));
    }

    @Test
    void shouldReturnErrorOnDeleteMethod() throws Exception {
        mockMvc.perform(delete("/DevOps")
                .header("X-Parse-REST-API-Key", apiKey)
                .header("X-JWT-KWY", jwtService.generateToken()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string("ERROR"));
    }
}
