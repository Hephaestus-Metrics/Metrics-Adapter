package com.example.droolsprototype.model.metrics.complex;

import com.example.droolsprototype.model.metrics.MetricTemplate;
import com.example.droolsprototype.model.promql.complexqueries.ComplexMetricResult;

import java.util.Map;

/**
 * Abstract class for complex metrics: VECTOR, MATRIX
 */
public abstract class ComplexMetricTemplate extends MetricTemplate {
    private final String name;
    private final ComplexMetricResult queryResult;
    private Map<String, String> labels;

    public ComplexMetricTemplate(ComplexMetricResult queryResult) {
        super(queryResult.getResultType());
        this.queryResult = queryResult;
        this.name = queryResult.getMetric().get("__name__");
        this.labels = queryResult.getMetric();
        this.getDataFromResult();
    }

    public String getName() {
        return name;
    }

    public ComplexMetricResult getQueryResult() {
        return queryResult;
    }

    public abstract void getDataFromResult();

    @Override
    public abstract String toString();

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
