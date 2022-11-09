package com.example.droolsprototype.demo.performancetests.demotasks;

import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.services.PrometheusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demo performing queries and firing mock rule engine rules
 */
@Component
public class DemoTaskWithMetricsNumLogging extends TimerTask {

    private final PrometheusQueryService queryService;
    private final StatelessKieSession kieSession;
    private final ExecutionService executionService;

    public DemoTaskWithMetricsNumLogging(PrometheusQueryService queryService, StatelessKieSession kieSession, ExecutionService executionService) {
        this.queryService = queryService;
        this.kieSession = kieSession;
        this.executionService = executionService;
    }

    public void run() {
        List<Metric> queryResults = queryService.queryMetrics();
        int numberOfMetrics = queryResults.size();

        List<Object> kieInput = Stream.concat(
                queryResults.stream(), Stream.of(executionService))
                .collect(Collectors.toList());

        kieSession.execute(kieInput);

        System.out.printf("Number of metrics: %s", numberOfMetrics);
    }
}
