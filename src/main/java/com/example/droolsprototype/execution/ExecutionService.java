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
    //this is just a mock for debug purposes
    //in a real system information about necessary changes could be posted on a designated endpoint
    // which the executor container would read
    // or there could be some other mechanism of letting the executor know the system requires adjusting
    //either way, implementing the executor is beyond the scope of this demo
    public void log(String message){
        System.out.println("Executor: " + message);
    }
}
