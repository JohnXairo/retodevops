package com.devops.reto.service;

import com.devops.reto.model.MessageRequest;
import com.devops.reto.model.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public class DevOpsService {

    public MessageResponse processMessage(MessageRequest request) {
        String message = String.format(
            "Hello %s your message will be sent", request.getTo()
        );
        return new MessageResponse(message);
    }
}
