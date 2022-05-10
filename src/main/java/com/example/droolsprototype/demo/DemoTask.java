package com.example.droolsprototype.demo;

import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.model.QueryInfo;
import com.example.droolsprototype.model.promql.AbstractQueryResult;
import com.example.droolsprototype.services.PrometheusQueryService;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Demo performing queries and firing mock rule engine rules
 */
@Component
public class DemoTask extends TimerTask {

    private final PrometheusQueryService queryService;
    private final KieSession kieSession;
    private final ExecutionService executionService;

    private String[] queries;

    private QueryInfo toQuery;
    private final StringBuilder logBuilder;

    public DemoTask(PrometheusQueryService queryService, KieSession kieSession, ExecutionService executionService) {
        this.queryService = queryService;
        this.kieSession = kieSession;
        this.executionService = executionService;
        List<String> list = new ArrayList<>();
        list.add("scalar({cpu=\"0\",__name__=\"node_cpu_guest_seconds_total\",mode=\"user\"})"); //todo add exception
        this.toQuery = new QueryInfo(list);
        this.logBuilder = new StringBuilder();
    }

    public void run() {
        QueryInfo toQuery = this.toQuery; //makes a local copy (so that it's immutable from the outside)
        //sending requests for metrics
        for (String query : toQuery.getQueries()) {
            try {
                AbstractQueryResult complexQueryResult = queryService.queryMetric(query);
                logBuilder.append(query).append("\n"); //log query
                // MetricTemplate metric = queryResult.getData().getResult()[0].toMetricObject();
                System.out.println((complexQueryResult.getMetricObjects().get(0)));
                //debug

                //give control of these objects to the drools engine
                //kieSession.insert(metric);
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                logBuilder.append(sw).append("\n");
                e.printStackTrace();
            }

        }
        //give control of executor
        kieSession.insert(executionService);

        //tell drools to evaluate all rules
        System.out.println("Running drools...");
        kieSession.fireAllRules();
    }

    public void setToQuery(QueryInfo toQuery) {
        this.toQuery = toQuery;
    }

    public QueryInfo getToQuery() {
        return toQuery;
    }

    public String getQueryLogs() {
        return logBuilder.toString();
    }

    public void setQueries(String[] queries) {
        this.queries = queries;
    }

}
