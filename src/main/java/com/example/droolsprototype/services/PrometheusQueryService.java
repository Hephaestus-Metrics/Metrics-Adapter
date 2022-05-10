package com.example.droolsprototype.services;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.promql.AbstractQueryResult;
import com.example.droolsprototype.model.promql.complexqueries.ComplexQueryResult;
import com.example.droolsprototype.model.promql.simplequeries.SimpleQueryResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service for making GET requests to the Prometheus service
 * The prometheus URL is provided as a commandline argument of the form --prometheus="..."
 */
@Service
public class PrometheusQueryService {

    private final RestTemplate restTemplate;

    private static String PROMETHEUS_URL;

    public PrometheusQueryService(RestTemplateBuilder restTemplateBuilder, @Value("${prometheus:}") String prometheusUrl) {
        PrometheusQueryService.PROMETHEUS_URL = prometheusUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public AbstractQueryResult queryMetric(String query) {
        String url = PROMETHEUS_URL + "/api/v12/query?query={some_query}";
        try {
            String resultString = restTemplate.getForObject(url, String.class, query);
            JSONObject resultJSON = new JSONObject(resultString);
            ResultTypes resultType = ResultTypes.valueOf(resultJSON.getJSONObject("data").
                    getString("resultType").toUpperCase());
            ObjectMapper objectMapper = new ObjectMapper();
            if (resultType == ResultTypes.VECTOR || resultType == ResultTypes.MATRIX) {
                return objectMapper.readValue(resultString, ComplexQueryResult.class);
            } else {
                return objectMapper.readValue(resultString, SimpleQueryResult.class);
            }
        } catch (RestClientException | JSONException | IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
