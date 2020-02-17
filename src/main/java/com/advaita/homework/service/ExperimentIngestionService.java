package com.advaita.homework.service;

import com.advaita.homework.csv.CsvService;
import com.advaita.homework.domain.AdvaitaExperiment;
import com.advaita.homework.domain.AnalysisContext;
import com.advaita.homework.domain.InputExperimentInstance;
import com.advaita.homework.factory.AdvaitaExperimentFactory;
import com.advaita.homework.factory.InputExperimentInstanceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
public class ExperimentIngestionService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private CsvService csvService;

    public void ingestExperiment(String inputFilePath, String outputFilePath) {

        List<Map<String, String>> experimentDataRows = csvService.readCsv(inputFilePath);
        if (CollectionUtils.isEmpty(experimentDataRows)) {
            throw new RuntimeException("There were no data rows to analyze");
        }

        Map<String, String> sampleDataRow = experimentDataRows.get(0);
        Set<String> recognizedExperimentLabels = parseRecognizedExperiments(sampleDataRow);

        Map<String, List<InputExperimentInstance>> experimentInstancesGroupedByLabel = experimentDataRows.stream()
                .map(row -> InputExperimentInstanceFactory.createMany(row, recognizedExperimentLabels))
                .flatMap(Set::stream)
                .collect(groupingBy(InputExperimentInstance::getExperimentLabel));

        List<AdvaitaExperiment> advaitaExperiments = experimentInstancesGroupedByLabel.entrySet().stream()
                .map(entry -> AdvaitaExperimentFactory.create(entry.getKey(), entry.getValue()))
                .collect(toList());

        writeExperimentOutputs(outputFilePath, advaitaExperiments);

    }

    private static Set<String> parseRecognizedExperiments(Map<String, String> sampleDataRow) {
        return sampleDataRow.keySet().stream()
                .map(ExperimentIngestionService::parseExperimentLabel)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    private static String parseExperimentLabel(String dataHeader) {
        if (dataHeader == null || dataHeader.length() < 4) {
            return null;
        }

        try {
            // Throws a runtime if analysis context cannot be properly parsed
            AnalysisContext.valueOf(StringUtils.upperCase(dataHeader.substring(0, 2)));
            return StringUtils.lowerCase(dataHeader.substring(3));
        } catch (RuntimeException e) {
            return null;
        }
    }

    private void writeExperimentOutputs(String outputFilePath, List<AdvaitaExperiment> advaitaExperiments) {
        File outputFile = new File(outputFilePath);

        try {
            MAPPER.writeValue(outputFile, advaitaExperiments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write output file");
        }

    }
}
