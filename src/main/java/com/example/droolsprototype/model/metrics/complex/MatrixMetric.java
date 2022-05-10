package com.example.droolsprototype.model.metrics.complex;

import com.example.droolsprototype.model.promql.complexqueries.ComplexMetricResult;
import com.example.droolsprototype.model.utilities.ValueConverter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Metric with matrix as value
 */
public class MatrixMetric extends ComplexMetricTemplate {

    private String[][] valueStrings;
    private ArrayList<Double> values;
    private ArrayList<Double> timestamps;

    public MatrixMetric(ComplexMetricResult queryResult) {
        super(queryResult);
    }

    @Override
    public void getDataFromResult() {
        this.values = new ArrayList<>();
        this.timestamps = new ArrayList<>();
        this.valueStrings = super.getQueryResult().getValues();
        for (String[] valueString : valueStrings) {
            double[] converted = ValueConverter.convert(valueString);
            this.timestamps.add(converted[0]);
            this.values.add(converted[1]);
        }
    }

    @Override
    public String toString() {
        return "MatrixMetric{" +
                "valueStrings=" + Arrays.toString(valueStrings) +
                ", values=" + values +
                ", timestamps=" + timestamps +
                ", labels=" + super.getLabels() +
                '}';
    }

    public ArrayList<Double> getValues() {
        return this.values;
    }

    public ArrayList<Double> getTimestamps() {
        return this.timestamps;
    }
}