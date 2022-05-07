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

    /**
     * Mock method, does nothing
     * Should let the executor container know the deployment needs to reduce CPU usage of the front-end service
     */
    public void reduceCPU() {
        System.out.println("Sending request to executor to reduce CPU usage");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        msg = "Received request to lower CPU at " + dtf.format(LocalDateTime.now());
        //this is just a mock for debug purposes
        //in a real system information about necessary changes could be posted on a designated endpoint
        // which the executor container would read
        // or there could be some other mechanism of letting the executor know the system requires adjusting
        //either way, implementing the executor is beyond the scope of this demo
    }
}
