package com.example.droolsprototype.model.promql.simplequeries;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.metrics.simple.ScalarMetric;
import com.example.droolsprototype.model.metrics.simple.SimpleMetricTemplate;
import com.example.droolsprototype.model.metrics.simple.StringMetric;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * POJO for JSON conversion
 */
public class SimpleData implements Serializable {

    private ResultTypes resultType;
    private String[] result;


    public SimpleData() {
        //required by Jackson
    }

    public ResultTypes getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = ResultTypes.valueOf(resultType.toUpperCase());
    }

    public String[] getResult() {
        return result;
    }

    public void setResult(String[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "resultType=" + resultType +
                ", result=" + Arrays.toString(result) +
                '}';
    }

    public ArrayList<SimpleMetricTemplate> getMetricObjects() {
        switch (resultType) {
            case STRING:
                return new ArrayList<>(List.of(new StringMetric(result, resultType)));
            case SCALAR:
                return new ArrayList<>(List.of(new ScalarMetric(result, resultType)));
            default:
                break;

        }
        return null;
    }

}
