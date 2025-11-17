package com.dev.org.export.strategy;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileGeneratorTest {

    private final ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator();

    @Test
    @DisplayName("Should generate Excel file with headers and data")
    void shouldGenerateExcelFileWithHeadersAndData() throws IOException {
        // Given
        String[] headers = {"ID", "Name", "Email"};
        List<Object[]> data = Arrays.asList(
                new Object[]{1, "John Doe", "john@example.com"},
                new Object[]{2, "Jane Smith", "jane@example.com"}
        );

        // When
        Resource resource = excelFileGenerator.generate(headers, data);

        // Then
        assertNotNull(resource);
        assertTrue(resource.exists());
        
        // Read the content of the generated Excel file
        try (InputStream is = resource.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            assertNotNull(sheet);
            
            // Verify headers
            Row headerRow = sheet.getRow(0);
            assertNotNull(headerRow);
            assertEquals(headers.length, headerRow.getPhysicalNumberOfCells());
            for (int i = 0; i < headers.length; i++) {
                assertEquals(headers[i], headerRow.getCell(i).getStringCellValue());
            }
            
            // Verify data rows
            assertEquals(data.size(), sheet.getPhysicalNumberOfRows() - 1); // Subtract header row
            
            // Check first data row
            Row firstDataRow = sheet.getRow(1);
            assertNotNull(firstDataRow);
            assertEquals(1.0, firstDataRow.getCell(0).getNumericCellValue());
            assertEquals("John Doe", firstDataRow.getCell(1).getStringCellValue());
            assertEquals("john@example.com", firstDataRow.getCell(2).getStringCellValue());
            
            // Check second data row
            Row secondDataRow = sheet.getRow(2);
            assertNotNull(secondDataRow);
            assertEquals(2.0, secondDataRow.getCell(0).getNumericCellValue());
            assertEquals("Jane Smith", secondDataRow.getCell(1).getStringCellValue());
            assertEquals("jane@example.com", secondDataRow.getCell(2).getStringCellValue());
        }
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
        Resource resource = excelFileGenerator.generate(headers, data);

        // Then
        assertNotNull(resource);
        
        // Read the content of the generated Excel file
        try (InputStream is = resource.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // Check first data row with null value
            Row firstDataRow = sheet.getRow(1);
            assertNotNull(firstDataRow);
            assertEquals(1.0, firstDataRow.getCell(0).getNumericCellValue());
            // Check that cell exists but is empty (blank cell type)
            assertNotNull(firstDataRow.getCell(1));
            assertEquals("", firstDataRow.getCell(1).getStringCellValue()); // Empty string for null value
            assertEquals("john@example.com", firstDataRow.getCell(2).getStringCellValue());
            
            // Check second data row with null value
            Row secondDataRow = sheet.getRow(2);
            assertNotNull(secondDataRow);
            assertEquals(2.0, secondDataRow.getCell(0).getNumericCellValue());
            assertEquals("Jane Smith", secondDataRow.getCell(1).getStringCellValue());
            // Check that cell exists but is empty (blank cell type)
            assertNotNull(secondDataRow.getCell(2));
            assertEquals("", secondDataRow.getCell(2).getStringCellValue()); // Empty string for null value
        }
    }

    @Test
    @DisplayName("Should return correct filename with Excel extension")
    void shouldReturnCorrectFilenameWithExcelExtension() {
        // Given
        String baseName = "export_data";
        
        // When
        String filename = excelFileGenerator.getFilename(baseName);
        
        // Then
        assertEquals("export_data.xlsx", filename);
    }

    @Test
    @DisplayName("Should return correct content type for Excel")
    void shouldReturnCorrectContentTypeForExcel() {
        // When
        String contentType = excelFileGenerator.getContentType();
        
        // Then
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", contentType);
    }
}
