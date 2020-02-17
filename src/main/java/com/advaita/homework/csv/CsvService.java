package com.advaita.homework.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class CsvService {

    public List<Map<String, String>> readCsv(String filePath) {
        Reader reader;
        try {
            Charset inputCharset = Charset.forName("ISO-8859-1");
            URL url = new ClassLoader().getResource(filePath);
            File file = new File(url.getFile());
            reader = Files.newBufferedReader(file.toPath(), inputCharset);
            List<String[]> rows = readAll(reader);
            String[] headerRow = rows.remove(0);
            return rows.stream()
                    .map(row -> convertRow(row, headerRow))
                    .collect(toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> readAll(Reader reader) throws Exception {

        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator('\t')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(csvParser)
                .build();

        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    private Map<String, String> convertRow(String[] row, String[] headerRow) {

        try {
            Map<String, String> map = new HashMap<>();

            int length = Math.min(headerRow.length, row.length);

            IntStream.range(0, length)
                    .forEach(i -> map.put(headerRow[i], row[i]));

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
