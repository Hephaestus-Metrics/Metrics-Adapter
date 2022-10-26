package com.example.droolsprototype.services;


import io.github.hephaestusmetrics.model.metrics.Metric;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import io.github.hephaestusmetrics.serialization.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service for making GET requests to the GUI for Prometheus values
 */
@Service
public class PrometheusQueryService {

    private final RestTemplate restTemplate;
    private final Translator translator = new Translator();

    private static String BACKEND_URL;

    public PrometheusQueryService(RestTemplateBuilder restTemplateBuilder, @Value("${backend}") String backendUrl) {
        PrometheusQueryService.BACKEND_URL = backendUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Metric> queryMetrics() {
        String url = BACKEND_URL + "/hephaestus/metrics/selected";
        RawQueryResult[] rawMetrics = restTemplate.getForObject(url, RawQueryResult[].class);
        return Arrays.stream(Objects.requireNonNullElse(rawMetrics, new RawQueryResult[]{}))
                .map(translator::parseVectorResult)
                .flatMap(result -> result.getAll().stream())
                .collect(Collectors.toList());
    }
}
