package com.devops.reto.service;

import com.devops.reto.model.MessageRequest;
import com.devops.reto.model.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevOpsServiceTest {

    private DevOpsService devOpsService;

    @BeforeEach
    void setUp() {
        devOpsService = new DevOpsService();
    }

    @Test
    void shouldReturnCorrectMessage() {
        MessageRequest request = new MessageRequest(
            "This is a test", "Juan Perez", "Rita Asturia", 45
        );
        MessageResponse response = devOpsService.processMessage(request);
        assertEquals("Hello Juan Perez your message will be sent", response.getMessage());
    }

    @Test
    void shouldHandleDifferentRecipients() {
        MessageRequest request = new MessageRequest(
            "Hello", "Maria Lopez", "Carlos Ruiz", 30
        );
        MessageResponse response = devOpsService.processMessage(request);
        assertEquals("Hello Maria Lopez your message will be sent", response.getMessage());
    }

    @Test
    void shouldNotReturnNullResponse() {
        MessageRequest request = new MessageRequest(
            "Test", "Ana Torres", "Luis Gomez", 10
        );
        assertNotNull(devOpsService.processMessage(request));
    }
}
