package com.example.droolsprototype.model.promql.complexqueries;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.metrics.complex.ComplexMetricTemplate;
import com.example.droolsprototype.model.metrics.complex.MatrixMetric;
import com.example.droolsprototype.model.metrics.complex.VectorMetric;

import java.util.Arrays;
import java.util.Map;

/**
 * POJO for JSON conversion
 */
public class ComplexMetricResult {

    private Map<String, String> metric;
    private String[] value;
    private String[][] values;
    private ResultTypes resultType;

    public Map<String, String> getMetric() {
        return metric;
    }

    public void setMetric(Map<String, String> metric) {
        this.metric = metric;
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public void setResultType(ResultTypes type) {
        this.resultType = type;
    }

    public ResultTypes getResultType() {
        return resultType;
    }

    @Override
    public String toString() {
        return "SingleMetricResult{" +
                "metric=" + metric +
                ", value=" + Arrays.toString(value) +
                ", values=" + Arrays.toString(values) +
                ", resultType=" + resultType +
                '}';
    }

    public void setValues(String[][] values) {
        this.values = values;
    }

    public String[][] getValues() {
        return values;
    }

    public ComplexMetricTemplate toMetricObject() {
        switch (resultType) {
            case VECTOR:
                return new VectorMetric(this);
            case MATRIX:
                return new MatrixMetric(this);
            default:
                break;

        }
        return null;
    }

}
