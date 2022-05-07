package com.example.droolsprototype.model.metrics.simple;

import com.example.droolsprototype.model.ResultTypes;

import java.util.Arrays;

public class StringMetric extends SimpleMetricTemplate {
    private String value;
    private double timestamp;
    private final String[] stringResult;

    public StringMetric(String[] result, ResultTypes resultType) {
        super(resultType);
        this.stringResult = result;
        this.getDataFromResult();
    }

    @Override
    public void getDataFromResult() {
        this.value = this.stringResult[1];
        try {
            timestamp = Double.parseDouble(this.stringResult[0]);
        } catch (NumberFormatException e) {
            timestamp = Double.NaN;
        }
    }

    @Override
    public String toString() {
        return "ScalarMetric{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                ", stringResult='" + Arrays.toString(stringResult) + '\'' +
                '}';
    }

    public String getValue() {
        return value;
    }

    public double getTimestamp() {
        return timestamp;
    }
}
