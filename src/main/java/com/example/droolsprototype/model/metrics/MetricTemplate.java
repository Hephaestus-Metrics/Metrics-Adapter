package com.example.droolsprototype.model.metrics;

import com.example.droolsprototype.model.ResultTypes;

/**
 * Most generic class for metrics
 */
public abstract class MetricTemplate {

    private final ResultTypes type;

    protected MetricTemplate(ResultTypes type) {
        this.type = type;
    }

    public abstract void getDataFromResult();

    @Override
    public abstract String toString();

    public ResultTypes getType() {
        return type;
    }
}
