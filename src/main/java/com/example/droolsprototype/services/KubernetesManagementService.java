package com.example.droolsprototype.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KubernetesManagementService {

    Logger logger = LoggerFactory.getLogger(KubernetesManagementService.class);

    private final RestTemplate restTemplate;
    private final String DELETE_POD_URL = "/kubernetes/management/pod/delete";

    public static String KUBERNETES_MANAGEMENT_URL;

    public KubernetesManagementService(RestTemplateBuilder restTemplateBuilder, @Value("${kubernetes-management}") String kubernetesManagementUrl) {
        KubernetesManagementService.KUBERNETES_MANAGEMENT_URL = kubernetesManagementUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public void deletePod(String namespace, String podName) {
        String url = KUBERNETES_MANAGEMENT_URL + DELETE_POD_URL;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("namespace", namespace);
        parameters.put("podName", podName);
        logger.info(String.format("Execute rules function with parameters: namespace - %s, pod name - %s"), namespace, podName);
        ResponseEntity response = restTemplate.getForEntity(url, ResponseEntity.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Execute function executed");
        } else {
            logger.error("Execute function failed");
        }
    }

}
