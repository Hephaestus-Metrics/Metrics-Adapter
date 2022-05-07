package com.example.droolsprototype.model.metrics.simple;

import com.example.droolsprototype.model.ResultTypes;
import com.example.droolsprototype.model.metrics.MetricTemplate;

/**
 * Abstract class for simple metrics: STRING, SCALAR
 */
public abstract class SimpleMetricTemplate extends MetricTemplate {

    public SimpleMetricTemplate(ResultTypes type) {
        super(type);
    }

    public abstract void getDataFromResult();


    @Override
    public abstract String toString();

}
