package com.example.droolsprototype.model.metrics.simple;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.utilities.ValueConverter;

import java.util.Arrays;

public class ScalarMetric extends SimpleMetricTemplate {
    private double value;
    private double timestamp;
    private final String[] stringResult;

    public ScalarMetric(String[] result, ResultTypes resultType) {
        super(resultType);
        this.stringResult = result;
        this.getDataFromResult();
    }

    @Override
    public void getDataFromResult() {
        double[] converted = ValueConverter.convert(this.stringResult);
        this.timestamp = converted[0];
        this.value = converted[1];
    }

    @Override
    public String toString() {
        return "ScalarMetric{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                ", stringResult='" + Arrays.toString(stringResult) + '\'' +
                '}';
    }

    public double getValue() {
        return value;
    }

    public double getTimestamp() {
        return timestamp;
    }
}
