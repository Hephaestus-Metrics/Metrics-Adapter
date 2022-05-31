package com.example.droolsprototype.services;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.promql.AbstractQueryResult;
import com.example.droolsprototype.model.promql.complexqueries.ComplexQueryResult;
import com.example.droolsprototype.model.promql.simplequeries.SimpleQueryResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for making GET requests to the GUI for Prometheus values
 */
@Service
public class PrometheusQueryService {

    private final RestTemplate restTemplate;

    private static String BACKEND_URL;

    public PrometheusQueryService(RestTemplateBuilder restTemplateBuilder, @Value("${backend:}") String backendUrl) {
        PrometheusQueryService.BACKEND_URL = backendUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<AbstractQueryResult> queryMetrics() {
        ArrayList<AbstractQueryResult> queryResults = new ArrayList<>();
        String url = BACKEND_URL + "/hephaestus/metrics/selected";
        try {
            String resultString = restTemplate.getForEntity(url, String.class).getBody();
            JSONObject resultJSON = new JSONObject(resultString);
            JSONArray resultJSONArray = resultJSON.getJSONArray("simpleMetrics");
            for (int i = 0; i < resultJSONArray.length(); i++){
                String queryString = resultJSONArray.getString(i);
                JSONObject queryResult = new JSONObject(queryString);
                ResultTypes resultType = ResultTypes.valueOf(queryResult.getJSONObject("data").
                        getString("resultType").toUpperCase());
                ObjectMapper objectMapper = new ObjectMapper();
                if (resultType == ResultTypes.VECTOR || resultType == ResultTypes.MATRIX) {
                    queryResults.add(objectMapper.readValue(queryString, ComplexQueryResult.class));
                } else {
                    queryResults.add(objectMapper.readValue(queryString, SimpleQueryResult.class));
                }
            }
            return queryResults;

        } catch (RestClientException | JSONException | IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
