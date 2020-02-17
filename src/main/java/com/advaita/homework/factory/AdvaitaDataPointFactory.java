package com.advaita.homework.factory;

import com.advaita.homework.domain.InputDataPoint;
import com.advaita.homework.domain.InputExperimentInstance;
import com.advaita.homework.domain.AdvaitaDataPoint;

import static com.advaita.homework.domain.AnalysisContext.FC;
import static com.advaita.homework.domain.AnalysisContext.PV;

public class AdvaitaDataPointFactory {

    public static AdvaitaDataPoint create(InputExperimentInstance inputExperimentInstance) {

        Double fcValue = null;
        Double pvValue = null;

        for (InputDataPoint inputDataPoint : inputExperimentInstance.getInputDataPoints()) {
            if (inputDataPoint.getAnalysisContext() == FC) {
                fcValue = inputDataPoint.getValue();
            } else if (inputDataPoint.getAnalysisContext() == PV) {
                pvValue = inputDataPoint.getValue();
            }
        }

        if (fcValue == null || pvValue == null) {
            return null;
        }

        return AdvaitaDataPoint.builder()
                .symbol(inputExperimentInstance.getSymbol())
                .fc(fcValue)
                .pv(pvValue)
                .build();
    }
}
