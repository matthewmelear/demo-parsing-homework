package com.advaita.homework.factory;

import com.advaita.homework.domain.InputDataPoint;
import com.advaita.homework.domain.InputExperimentInstance;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.advaita.homework.domain.AnalysisContext.FC;
import static com.advaita.homework.domain.AnalysisContext.PV;
import static com.advaita.homework.factory.DataPointFactory.create;
import static com.advaita.homework.util.Constants.SYMBOL;

public class InputExperimentInstanceFactory {

    public static Set<InputExperimentInstance> createMany(Map<String, String> row, Set<String> recognizedExperimentLabels) {

        String symbol = row.get(SYMBOL);
        Set<InputExperimentInstance> inputExperimentInstances = new HashSet<>();

        for (String recognizedExperimentLabel : recognizedExperimentLabels) {
            String pvKey = pvKey(recognizedExperimentLabel);
            InputDataPoint pvInputDataPoint = create(row.get(pvKey), PV);

            String fcKey = fcKey(recognizedExperimentLabel);
            InputDataPoint fcInputDataPoint = create(row.get(fcKey), FC);

            if (pvInputDataPoint != null && fcInputDataPoint != null) {
                InputExperimentInstance inputExperimentInstance = InputExperimentInstance.builder()
                        .experimentLabel(recognizedExperimentLabel)
                        .inputDataPoints(Sets.newHashSet(pvInputDataPoint, fcInputDataPoint))
                        .symbol(symbol)
                        .build();

                inputExperimentInstances.add(inputExperimentInstance);
            }

        }

        return inputExperimentInstances;
    }

    private static String pvKey(String experimentLabel) {
        return "pv_" + experimentLabel;
    }

    private static String fcKey(String experimentLabel) {
        return "fc_" + experimentLabel;
    }
}
