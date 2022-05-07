package com.example.droolsprototype.model.promql.complexqueries;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.metrics.complex.ComplexMetricTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * POJO for JSON conversion
 */
public class ComplexData implements Serializable {

    private ResultTypes resultType;
    private ComplexMetricResult[] result;


    public ComplexData() {
        //required by Jackson
    }

    public ResultTypes getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = ResultTypes.valueOf(resultType.toUpperCase());
    }

    public ComplexMetricResult[] getResult() {
        return result;
    }

    public void setResult(ComplexMetricResult[] result) {
        this.result = result;
        for (ComplexMetricResult singleRes : this.result) {
            singleRes.setResultType(this.resultType);
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "resultType=" + resultType +
                ", result=" + Arrays.toString(result) +
                '}';
    }

    public ArrayList<ComplexMetricTemplate> getMetricObjects() {

        ArrayList<ComplexMetricTemplate> res = new ArrayList<>();
        for (ComplexMetricResult m : this.result) {
            res.add(m.toMetricObject());
        }
        return res;
    }

}
