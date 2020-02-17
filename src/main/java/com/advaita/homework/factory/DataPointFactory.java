package com.advaita.homework.factory;

import com.advaita.homework.domain.AnalysisContext;
import com.advaita.homework.domain.InputDataPoint;
import org.apache.commons.lang3.StringUtils;

public class DataPointFactory {

    public static InputDataPoint create(String rawDataPointValue, AnalysisContext analysisContext) {

        Double value = parseAsDouble(rawDataPointValue);

        if (value != null && analysisContext != null) {
            return InputDataPoint.builder()
                    .value(value)
                    .analysisContext(analysisContext)
                    .build();
        }

        return null;

    }

    private static Double parseAsDouble(String rawValue) {

        if (StringUtils.isEmpty(rawValue)) {
            return null;
        }

        try {
            return Double.valueOf(rawValue);
        } catch (NumberFormatException nfe) {
            // Was not a straight double :(
            // Hopefully we have something in scientific notation...
            try {
                return readAsScientificNotation(rawValue);
            } catch (Exception e) {
                return null;
            }
        }

    }

    private static double readAsScientificNotation(String rawValue) {

        int indexOfE = rawValue.indexOf("E");
        Double rawValueLeftOfExpIndicator = Double.valueOf(rawValue.substring(0, indexOfE));

        int exponentQualifierIndex = rawValue.indexOf("+");
        if (exponentQualifierIndex < 0) {
            exponentQualifierIndex = rawValue.indexOf("-");
        }
        if (exponentQualifierIndex < 0) {
            exponentQualifierIndex = indexOfE + 1;
        }
        // ExponentQualifierIndex is set so that - is retained on the substring if exponent is negative
        double exponent = Double.valueOf(rawValue.substring(exponentQualifierIndex));

        return rawValueLeftOfExpIndicator * Math.pow(10, exponent);

    }
}
