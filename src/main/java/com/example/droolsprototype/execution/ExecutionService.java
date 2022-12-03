package com.example.droolsprototype.execution;

import com.example.droolsprototype.services.KubernetesManagementService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Mock class simulating this service communicating with some executor, who makes changes in the system
 */
@Service
@RestController
public class ExecutionService {

    private KubernetesManagementService kubernetesManagementService;

    public ExecutionService(KubernetesManagementService kubernetesManagementService) {
        this.kubernetesManagementService = kubernetesManagementService;
    }

    private String msg = "None for now";

    @GetMapping("/")
    public String debugMessageEndpoint() {
        return msg;
    }
    public void log(String message){
        System.out.println("Executor: " + message);
    }

    public void execute(String message, String message2){}

    public void deletePod(String namespace, String podName) {
        this.kubernetesManagementService.deletePod(namespace, podName);
    }
}
