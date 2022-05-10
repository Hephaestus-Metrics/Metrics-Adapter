package com.example.droolsprototype.model.metrics.complex;

import com.example.droolsprototype.model.promql.complexqueries.ComplexMetricResult;
import com.example.droolsprototype.model.utilities.ValueConverter;

import java.util.Arrays;

/**
 * Metric with vector as value
 */
public class VectorMetric extends ComplexMetricTemplate {

    private String[] valueString;

    private double value;
    private double timestamp;

    public VectorMetric(ComplexMetricResult queryResult) {
        super(queryResult);
        this.getDataFromResult();
    }

    @Override
    public void getDataFromResult() {
        this.valueString = super.getQueryResult().getValue();
        double[] converted = ValueConverter.convert(valueString);
        this.timestamp = converted[0];
        this.value = converted[1];
    }

    @Override
    public String toString() {
        return "VectorMetric{" +
                "valueString=" + Arrays.toString(valueString) +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", labels=" + super.getLabels() +
                '}';
    }

    public double getValue() {
        return value;
    }

    public double getTimestamp() {
        return timestamp;
    }
}
