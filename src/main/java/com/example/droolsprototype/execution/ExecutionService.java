package com.example.droolsprototype.execution;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mock class simulating this service communicating with some executor, who makes changes in the system
 */
@Service
@RestController
public class ExecutionService {

    private String msg = "None for now";

    @GetMapping("/")
    public String debugMessageEndpoint() {
        return msg;
    }
    public void execute(String message){
        System.out.println("Executor: " + message);
    }

    public void execute(String message, String message2){}
}
