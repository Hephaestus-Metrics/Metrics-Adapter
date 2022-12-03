package com.example.droolsprototype.demo.tests.utils;

import io.github.hephaestusmetrics.model.ResultType;
import io.github.hephaestusmetrics.model.metrics.Metric;

import java.util.*;

public class MockMetricsUtils {
    private static final String RELEVANT_METRIC_1 = "namespace";
    private static final String RELEVANT_METRIC_2 = "container";
    private static final String IRRELEVANT_METRIC = "irrelevantLabel";
    private static final String RELEVANT_LABEL_VAL_1 = "sock-shop";
    private static final String RELEVANT_LABEL_VAL_2 = "front-end";
    private static final String IRRELEVANT_LABEL_VAL= "irrelevantVal";
    private static final String RELEVANT_NAME = "container_cpu_usage_seconds_total";
    private static final String IRRELEVANT_NAME = "irrelevantName";
    private static final double RELEVANT_METRIC_1_PROBABILITY = 0.5;
    private static final double RELEVANT_METRIC_2_PROBABILITY = 0.5;
    private static final double NEXT_IRRELEVANT_METRIC_PROBABILITY = 0.85;
    private static final double RELEVANT_NAME_PROBABILITY = 0.4;
    private final static Random random = new Random(42);

    public final static Map<String, List<String>> POSSIBLE_LABEL_VALUES = Map.of(
            RELEVANT_METRIC_1, List.of(RELEVANT_LABEL_VAL_1, IRRELEVANT_LABEL_VAL + 1, IRRELEVANT_LABEL_VAL + 2),
            RELEVANT_METRIC_2, List.of(RELEVANT_LABEL_VAL_2, IRRELEVANT_LABEL_VAL + 1, IRRELEVANT_LABEL_VAL + 2),
            IRRELEVANT_METRIC, List.of(IRRELEVANT_LABEL_VAL + 1, IRRELEVANT_LABEL_VAL + 2 + IRRELEVANT_LABEL_VAL + 3));

    public static List<Metric> getSampleMetrics(int num) {
        List<Metric> result = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            result.add(generateSingleMetric());
        }
        return result;
    }

    public static Metric getRelevantMetric() {
        Map<String, String> labels = Map.of(
            "__name__", RELEVANT_NAME,
                RELEVANT_METRIC_1, RELEVANT_LABEL_VAL_1,
                RELEVANT_METRIC_2, RELEVANT_LABEL_VAL_2);
        return new Metric("MOCK-TAG", ResultType.SCALAR, labels, getSampleValue(), getSampleValue());
    }

    public static Metric getIrrelevantMetric() {
        Map<String, String> labels = Map.of(
                "__name__", RELEVANT_NAME,
                RELEVANT_METRIC_1, RELEVANT_LABEL_VAL_1,
                RELEVANT_METRIC_2, RELEVANT_LABEL_VAL_2);
        return new Metric("MOCK-TAG", ResultType.SCALAR, labels, getSampleValue(), getSampleValue());
    }

    private static Metric generateSingleMetric() {
        var labels = getSampleLabels();
        labels.put("__name__", (random.nextDouble() < RELEVANT_NAME_PROBABILITY) ? RELEVANT_NAME : IRRELEVANT_NAME);
        return new Metric("MOCK-TAG", ResultType.SCALAR, labels, getSampleValue(), getSampleValue());
    }

    private static double getSampleValue() {
        return random.nextDouble() * 5_000_000;
    }

    private static Map<String, String> getSampleLabels() {
        Map<String, String> labels = new LinkedHashMap<>();
        if (random.nextDouble() < RELEVANT_METRIC_1_PROBABILITY) {
            labels.put(RELEVANT_METRIC_1, getSampleLabelValue(RELEVANT_METRIC_1));
        }
        if (random.nextDouble() < RELEVANT_METRIC_2_PROBABILITY) {
            labels.put(RELEVANT_METRIC_2, getSampleLabelValue(RELEVANT_METRIC_2));
        }
        int i = 0;
        while (random.nextDouble() < NEXT_IRRELEVANT_METRIC_PROBABILITY) {
            labels.put(IRRELEVANT_METRIC + i, getSampleLabelValue(IRRELEVANT_METRIC));
        }
        return labels;
    }

    private static String getSampleLabelValue(String label) {
        List<String> possibleValues = POSSIBLE_LABEL_VALUES.get(label);
        return possibleValues.get(random.nextInt(possibleValues.size()));
    }
}
