package com.devops.reto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String message;
    private String to;
    private String from;
    private int timeToLifeSec;
}
