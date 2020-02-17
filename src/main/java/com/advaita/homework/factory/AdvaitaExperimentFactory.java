package com.advaita.homework.factory;

import com.advaita.homework.domain.AdvaitaDataPoint;
import com.advaita.homework.domain.AdvaitaExperiment;
import com.advaita.homework.domain.InputExperimentInstance;

import java.util.List;
import java.util.stream.Collectors;

public class AdvaitaExperimentFactory {

    public static AdvaitaExperiment create(String label, List<InputExperimentInstance> experimentInstances)  {

        List<AdvaitaDataPoint> advaitaDataPoints = experimentInstances.stream()
                .map(AdvaitaDataPointFactory::create)
                .collect(Collectors.toList());

        return AdvaitaExperiment.builder()
                .name(label)
                .data(advaitaDataPoints)
                .build();
    }

}
