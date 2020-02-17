package com.advaita.homework.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InputDataPoint {

    private AnalysisContext analysisContext;
    private Double value;

}
