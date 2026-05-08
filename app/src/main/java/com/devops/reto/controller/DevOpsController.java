package com.devops.reto.controller;

import com.devops.reto.model.MessageRequest;
import com.devops.reto.model.MessageResponse;
import com.devops.reto.service.DevOpsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DevOps")
public class DevOpsController {

    private final DevOpsService devOpsService;

    public DevOpsController(DevOpsService devOpsService) {
        this.devOpsService = devOpsService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> handlePost(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(devOpsService.processMessage(request));
    }

    @GetMapping
    public ResponseEntity<String> handleGet() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("ERROR");
    }

    @PutMapping
    public ResponseEntity<String> handlePut() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("ERROR");
    }

    @DeleteMapping
    public ResponseEntity<String> handleDelete() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("ERROR");
    }
}
