package com.advaita.homework.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class InputExperimentInstance {

    private String experimentLabel;
    private String symbol;
    private Set<InputDataPoint> inputDataPoints;

}
