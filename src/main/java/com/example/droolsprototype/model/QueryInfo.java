package com.example.droolsprototype.model;

import java.util.List;

public class QueryInfo {

    private List<String> queries;

    public QueryInfo() {
        // required by jackson
    }

    public QueryInfo(String... queries) {
        this.queries = List.of(queries);
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }
}
