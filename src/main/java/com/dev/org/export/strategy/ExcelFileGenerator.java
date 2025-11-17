package com.dev.org.export.strategy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Concrete implementation of FileGeneratorStrategy for Excel file format.
 * Uses Apache POI library to generate Excel files.
 */
@Component
public class ExcelFileGenerator implements FileGeneratorStrategy {

    private static final String SHEET_NAME = "Export Data";

    @Override
    public Resource generate(String[] headers, List<Object[]> data) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // Create data rows
            int rowNum = 1;
            for (Object[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                
                for (int i = 0; i < rowData.length; i++) {
                    Cell cell = row.createCell(i);
                    
                    if (rowData[i] != null) {
                        if (rowData[i] instanceof Number number) {
                            cell.setCellValue(number.doubleValue());
                        } else if (rowData[i] instanceof Boolean bool) {
                            cell.setCellValue(bool);
                        } else {
                            cell.setCellValue(rowData[i].toString());
                        }
                    }
                }
            }
            
            // Auto-size columns for better readability
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
            
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to generate Excel file", e);
        }
    }

    @Override
    public String getFilename(String baseName) {
        return baseName + ".xlsx";
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}
