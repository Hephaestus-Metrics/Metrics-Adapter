package com.example.droolsprototype.demo.tests.performance;

import com.example.droolsprototype.demo.SimpleDemoTask;
import com.example.droolsprototype.demo.tests.utils.CSVUtils;
import com.example.droolsprototype.execution.ExecutionService;
import com.example.droolsprototype.services.PrometheusQueryService;
import io.github.hephaestusmetrics.model.metrics.Metric;
import io.github.hephaestusmetrics.model.queryresults.RawQueryResult;
import io.github.hephaestusmetrics.serialization.Translator;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demo performing queries and firing mock rule engine rules
 */
@Component
public class DemoTaskWithTimeMeasurement extends SimpleDemoTask {

    @Value("${test.save.path}")
    private String savePath;

    private final Translator translator = new Translator();

    public DemoTaskWithTimeMeasurement(PrometheusQueryService queryService, StatelessKieSession kieSession, ExecutionService executionService) {
        super(queryService, kieSession, executionService);
    }

    public void run() {
        var timeBeforeQuerying = System.currentTimeMillis();
        RawQueryResult[] rawMetrics = queryService.queryMetricsRaw();
        var timeAfterQuerying = System.currentTimeMillis();
        List<Metric> queryResults = Arrays.stream(Objects.requireNonNullElse(rawMetrics, new RawQueryResult[]{}))
                .map(translator::parseResult)
                .flatMap(result -> result.getMetrics().stream())
                .collect(Collectors.toList());
        var timeAfterParse = System.currentTimeMillis();
        int numberOfMetrics = queryResults.size();
        List<Object> kieInput = Stream.concat(
                        queryResults.stream(), Stream.of(executionService))
                .collect(Collectors.toList());
        Collections.shuffle(kieInput);
        var timeBeforeExecution = System.currentTimeMillis();
        executionService.insertTimes(numberOfMetrics, timeBeforeQuerying, timeAfterQuerying, timeBeforeExecution, timeAfterParse);
        kieSession.execute(kieInput);

    }
}
