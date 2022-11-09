package com.example.droolsprototype.demo.performancetests.demotasks;

import com.example.droolsprototype.demo.performancetests.utils.CSVUtils;
import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.services.PrometheusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demo performing queries and firing mock rule engine rules
 */
@Component
public class DemoTaskWithTimeMeasurement extends TimerTask {

    private final PrometheusQueryService queryService;
    private final StatelessKieSession kieSession;
    private final ExecutionService executionService;

    @Value("${test.save.path}")
    private String savePath;

    public DemoTaskWithTimeMeasurement(PrometheusQueryService queryService, StatelessKieSession kieSession, ExecutionService executionService) {
        this.queryService = queryService;
        this.kieSession = kieSession;
        this.executionService = executionService;
    }

    public void run() {
        var timeBeforeQuerying = System.currentTimeMillis();
        List<Metric> queryResults = queryService.queryMetrics();
        var timeAfterQuerying = System.currentTimeMillis();

        int numberOfMetrics = queryResults.size();
        List<Object> kieInput = Stream.concat(
                        queryResults.stream(), Stream.of(executionService))
                .collect(Collectors.toList());

        var timeBeforeExecution = System.currentTimeMillis();
        kieSession.execute(kieInput);
        var timeAfterExecution = System.currentTimeMillis();

        CSVUtils.saveToCsv(savePath, List.of(numberOfMetrics, timeAfterQuerying - timeBeforeQuerying, timeAfterExecution - timeBeforeExecution));
    }
}