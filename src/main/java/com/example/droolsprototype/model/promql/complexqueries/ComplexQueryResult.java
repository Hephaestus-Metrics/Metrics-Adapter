package com.example.droolsprototype.model.promql.complexqueries;

import com.example.droolsprototype.model.metrics.complex.ComplexMetricTemplate;
import com.example.droolsprototype.model.promql.AbstractQueryResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * POJO for JSON conversion
 */
public class ComplexQueryResult extends AbstractQueryResult implements Serializable {

    private ComplexData complexData;

    public ComplexQueryResult() {
        //required by Jackson
    }

    public ComplexData getData() {
        return complexData;
    }

    public void setData(ComplexData complexData) {
        this.complexData = complexData;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "status='" + super.getStatus() + '\'' +
                ", data=" + complexData +
                '}';
    }

    /**
     * @return Metrics received from query
     */
    @Override
    public ArrayList<ComplexMetricTemplate> getMetricObjects() {
        return this.complexData.getMetricObjects();
    }

}
