package com.example.droolsprototype.demo.performancetests.demotasks;

import com.example.droolsprototype.demo.performancetests.utils.CSVUtils;
import com.example.droolsprototype.demo.performancetests.utils.MockMetricsUtils;
import com.example.droolsprototype.execution.ExecutionService;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DemoTaskWithMockedMetrics {
    private final StatelessKieSession kieSession;
    private final ExecutionService executionService;
    private static final int START = 10000;
    private static final int STOP = 10001;
    private static final int STEP = 1;


    @Value("${test.save.path}")
    private String savePath;

    public DemoTaskWithMockedMetrics(StatelessKieSession kieSession, ExecutionService executionService) {
        this.kieSession = kieSession;
        this.executionService = executionService;
    }

    public void run() {
        for (int metricsNum = START; metricsNum < STOP; metricsNum+= STEP) {
            List<Object> kieInput = Stream.concat(
                    MockMetricsUtils.getSampleMetrics(metricsNum).stream(), Stream.of(executionService))
                    .collect(Collectors.toList());

            var timeBeforeExecution = System.currentTimeMillis();
            System.out.println(kieInput.size());
            kieSession.execute(kieInput);
            var timeAfterExecution = System.currentTimeMillis();

            CSVUtils.saveToCsv(savePath, List.of(metricsNum, timeAfterExecution - timeBeforeExecution));
        }
    }
}
