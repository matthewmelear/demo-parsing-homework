package com.advaita.homework;

import com.advaita.homework.service.ExperimentIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ExperimentIngestionService experimentIngestionService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        String inputFilePath = "inputFile.txt";
        String outputFilePath = "outputFile.json";

        experimentIngestionService.ingestExperiment(inputFilePath, outputFilePath);
    }
}
