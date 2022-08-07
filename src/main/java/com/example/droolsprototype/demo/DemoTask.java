package com.example.droolsprototype.demo;

import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.services.PrometheusQueryService;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.hephaestusmetrics.model.promql.AbstractQueryResult;
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
    private final StatelessKieSession kieSession;
    private final ExecutionService executionService;
    private final StringBuilder logBuilder;

    Logger logger = LoggerFactory.getLogger(DemoTask.class);

    public DemoTask(PrometheusQueryService queryService, StatelessKieSession kieSession, ExecutionService executionService) {
        this.queryService = queryService;
        this.kieSession = kieSession;
        this.executionService = executionService;
        this.logBuilder = new StringBuilder();
    }

    public void run() {
        //sending requests for metrics
        boolean empty = true;
        List<Object> kieInput = new ArrayList<>();
        try {
            List<AbstractQueryResult> queryResults = queryService.queryMetrics();
            empty = queryResults.isEmpty();
            for (AbstractQueryResult queryResult : queryResults){
                kieInput.addAll(queryResult.getMetricObjects());
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
        kieInput.add(executionService);

        //tell drools to evaluate all rules if any metric has been added
        if (!empty) {
            logger.info("Running drools...");
            kieSession.execute(kieInput);
        }

    }

    public String getQueryLogs() {
        return logBuilder.toString();
    }
}
