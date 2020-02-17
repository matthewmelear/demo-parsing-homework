package com.advaita.homework.domain;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AdvaitaDataPoint {

    private String symbol;
    private double pv;
    private double fc;

}
