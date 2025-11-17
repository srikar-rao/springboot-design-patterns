package com.dev.org.export.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileGeneratorTest {

    private final CsvFileGenerator csvFileGenerator = new CsvFileGenerator();

    @Test
    @DisplayName("Should generate CSV file with headers and data")
    void shouldGenerateCsvFileWithHeadersAndData() throws IOException {
        // Given
        String[] headers = {"ID", "Name", "Email"};
        List<Object[]> data = Arrays.asList(
                new Object[]{1, "John Doe", "john@example.com"},
                new Object[]{2, "Jane Smith", "jane@example.com"}
        );

        // When
        Resource resource = csvFileGenerator.generate(headers, data);

        // Then
        assertNotNull(resource);
        assertTrue(resource.exists());
        
        // Read the content of the generated CSV file
        String content = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));
        
        // Verify the content
        assertTrue(content.contains("\"ID\",\"Name\",\"Email\""));
        assertTrue(content.contains("\"1\",\"John Doe\",\"john@example.com\""));
        assertTrue(content.contains("\"2\",\"Jane Smith\",\"jane@example.com\""));
    }

    @Test
    @DisplayName("Should handle null values in data")
    void shouldHandleNullValuesInData() throws IOException {
        // Given
        String[] headers = {"ID", "Name", "Email"};
        List<Object[]> data = Arrays.asList(
                new Object[]{1, null, "john@example.com"},
                new Object[]{2, "Jane Smith", null}
        );

        // When
        Resource resource = csvFileGenerator.generate(headers, data);

        // Then
        assertNotNull(resource);
        
        // Read the content of the generated CSV file
        String content = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));
        
        // Verify the content
        assertTrue(content.contains("\"1\",\"\",\"john@example.com\""));
        assertTrue(content.contains("\"2\",\"Jane Smith\",\"\""));
    }

    @Test
    @DisplayName("Should return correct filename with CSV extension")
    void shouldReturnCorrectFilenameWithCsvExtension() {
        // Given
        String baseName = "export_data";
        
        // When
        String filename = csvFileGenerator.getFilename(baseName);
        
        // Then
        assertEquals("export_data.csv", filename);
    }

    @Test
    @DisplayName("Should return correct content type for CSV")
    void shouldReturnCorrectContentTypeForCsv() {
        // When
        String contentType = csvFileGenerator.getContentType();
        
        // Then
        assertEquals("text/plain", contentType);
    }
}
