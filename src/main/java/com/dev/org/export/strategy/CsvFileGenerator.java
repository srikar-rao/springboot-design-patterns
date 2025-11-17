package com.dev.org.export.strategy;

import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * Concrete implementation of FileGeneratorStrategy for CSV file format.
 * Uses OpenCSV library to generate CSV files.
 */
@Component
public class CsvFileGenerator implements FileGeneratorStrategy {

    @Override
    public Resource generate(String[] headers, List<Object[]> data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            
            // Write headers
            writer.writeNext(headers);
            
            // Write data rows
            for (Object[] row : data) {
                // Convert all objects to strings
                String[] stringRow = Stream.of(row)
                        .map(obj -> obj != null ? obj.toString() : "")
                        .toArray(String[]::new);
                
                writer.writeNext(stringRow);
            }
            
            writer.flush();
            return new ByteArrayResource(outputStream.toByteArray());
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV file", e);
        }
    }

    @Override
    public String getFilename(String baseName) {
        return baseName + ".csv";
    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_PLAIN_VALUE;
    }
}
