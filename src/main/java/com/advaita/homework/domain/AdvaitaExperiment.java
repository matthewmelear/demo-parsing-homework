package com.advaita.homework.domain;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AdvaitaExperiment {

    private String name;
    private List<AdvaitaDataPoint> data;

}
