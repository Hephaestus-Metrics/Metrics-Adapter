package com.example.droolsprototype.model.utilities;

/**
 * Converts [ <unix_time>, "<sample_value>" ] from string to doubles
 */
public final class ValueConverter {

    private ValueConverter() {
    }

    ;

    public static double[] convert(String[] valueString) {
        double timestamp;
        double value;
        try {
            timestamp = Double.parseDouble(valueString[0]);
        } catch (NumberFormatException e) {
            timestamp = Double.NaN;
        }
        try {
            value = Double.parseDouble(valueString[1]);
        } catch (NumberFormatException e) {
            if (valueString[1].equalsIgnoreCase("inf")) {
                value = Double.POSITIVE_INFINITY;
            } else if (valueString[1].equalsIgnoreCase("-inf")) {
                value = Double.NEGATIVE_INFINITY;
            } else {
                value = Double.NaN;
            }
        }
        return new double[]{timestamp, value};
    }
}
