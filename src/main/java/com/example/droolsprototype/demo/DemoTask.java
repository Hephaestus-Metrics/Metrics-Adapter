package com.example.droolsprototype.demo;

import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.model.metrics.MetricTemplate;
import com.example.droolsprototype.model.promql.AbstractQueryResult;
import com.example.droolsprototype.services.PrometheusQueryService;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
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

    private final StringBuilder logBuilder;

    public DemoTask(PrometheusQueryService queryService, KieSession kieSession, ExecutionService executionService) {
        this.queryService = queryService;
        this.kieSession = kieSession;
        this.executionService = executionService;
        this.logBuilder = new StringBuilder();
    }

    public void run() {
        //sending requests for metrics
        boolean empty = true;
        try {
            List<AbstractQueryResult> queryResults = queryService.queryMetrics();
            empty = queryResults.isEmpty();
            for (AbstractQueryResult queryResult : queryResults){
                for (MetricTemplate metric: queryResult.getMetricObjects()){
                    kieSession.insert(metric);
                }
                System.out.println((queryResult.getMetricObjects().get(0)));
            }
            //debug
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logBuilder.append(sw).append("\n");
            e.printStackTrace();
        }


        //give control of executor
        kieSession.insert(executionService);


        //tell drools to evaluate all rules if any metric has been added
        if (!empty){
            System.out.println("Running drools...");
            kieSession.fireAllRules();
        }

    }

    public String getQueryLogs() {
        return logBuilder.toString();
    }
}
