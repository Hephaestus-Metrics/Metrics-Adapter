package com.example.droolsprototype.demo;

import com.example.droolsprototype.demo.tests.performance.DemoTaskWithMockedMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class DemoTaskRunner {
    private final TimerTask demoTask;
    private final TimerTask demoTaskWithTimeMeasurement;
    private final TimerTask demoTaskWithMetricsNumLogging;
    private final DemoTaskWithMockedMetrics demoTaskWithMockedMetrics;
    private final Timer timer = new Timer();

    @Value("${mode}")
    private String mode;

    @Value("${loop.delay}")
    private int delay;

    public DemoTaskRunner(TimerTask demoTask,
                          TimerTask demoTaskWithTimeMeasurement,
                          TimerTask demoTaskWithMetricsNumLogging,
                          DemoTaskWithMockedMetrics demoTaskWithMockedMetrics) {
        this.demoTask = demoTask;
        this.demoTaskWithTimeMeasurement = demoTaskWithTimeMeasurement;
        this.demoTaskWithMetricsNumLogging = demoTaskWithMetricsNumLogging;
        this.demoTaskWithMockedMetrics = demoTaskWithMockedMetrics;
    }

    public void run() {
        switch (mode) {
            case "TIME_TEST":
                timer.scheduleAtFixedRate(demoTaskWithTimeMeasurement, 0, delay);
                break;
            case "NUMBER_TEST":
            case "BUSINESS_DEMO_TEST":
                timer.scheduleAtFixedRate(demoTaskWithMetricsNumLogging, 0, delay);
                break;
            case "MOCK_METRICS_TEST":
                demoTaskWithMockedMetrics.run();
                break;
            default:
                timer.scheduleAtFixedRate(demoTask, 0, delay);
                break;
        }
    }
}
