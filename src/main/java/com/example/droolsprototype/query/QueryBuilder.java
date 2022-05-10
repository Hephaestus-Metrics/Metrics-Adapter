package com.example.droolsprototype.query;

import com.example.droolsprototype.model.QueryInfo;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private final String[][] metrics;
    private final List<String> queries;
    public QueryBuilder(String[][] metrics) {
        this.queries = new ArrayList<>();
        this.metrics = metrics;
    }

    public void buildQueries() {
        for(String[] metric: this.metrics) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for(String labels: metric) {
                String[] parameter = labels.split(":", 2);
                sb.append(parameter[0])
                        .append("=\"")
                        .append(parameter[1].trim())
                        .append("\"")
                        .append(", ");

            }
            sb.setLength(sb.length() - 2);
            sb.append("}");
            this.queries.add(sb.toString());
        }
    }

    public QueryInfo getQueries() {
        return new QueryInfo(this.queries);
    }

}
