package com.example.droolsprototype.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hephaestusmetrics.model.ResultTypes;
import io.github.hephaestusmetrics.model.promql.AbstractQueryResult;
import io.github.hephaestusmetrics.model.promql.complexqueries.ComplexQueryResult;
import io.github.hephaestusmetrics.model.promql.simplequeries.SimpleQueryResult;
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

import static com.example.droolsprototype.conf.Configuration.SELECTED_METRICS;

/**
 * Service for making GET requests to the GUI for Prometheus values
 */
@Service
public class PrometheusQueryService {

    private final RestTemplate restTemplate;

    private static String url;

    public PrometheusQueryService(RestTemplateBuilder restTemplateBuilder, @Value("${backend:}") String backendUrl) {
        this.url = backendUrl + SELECTED_METRICS;
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<AbstractQueryResult> queryMetrics() {
        ArrayList<AbstractQueryResult> queryResults = new ArrayList<>();
        try {
            prepareData(queryResults);
            return queryResults;
        } catch (RestClientException | JSONException | IllegalArgumentException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void prepareData(ArrayList<AbstractQueryResult> queryResults) throws JSONException, JsonProcessingException {
        String resultString = restTemplate.getForEntity(url, String.class).getBody();
        JSONObject resultJSON = new JSONObject(resultString);
        JSONArray resultJSONArray = resultJSON.getJSONArray("simpleMetrics");
        for (int i = 0; i < resultJSONArray.length(); i++) {
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
    }
}
