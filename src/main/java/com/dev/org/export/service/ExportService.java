package com.dev.org.export.service;

import com.dev.org.export.decorator.CompressionDecorator;
import com.dev.org.export.factory.ExporterFactory;
import com.dev.org.export.model.ExportResponse;
import com.dev.org.export.model.ExporterType;
import com.dev.org.export.model.FileFormat;
import com.dev.org.export.strategy.CsvFileGenerator;
import com.dev.org.export.strategy.ExcelFileGenerator;
import com.dev.org.export.strategy.FileGeneratorStrategy;
import com.dev.org.export.template.AbstractExporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that orchestrates the export workflow.
 * Coordinates between the exporter factory, exporters, and file generators.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    private final ExporterFactory exporterFactory;
    private final CsvFileGenerator csvFileGenerator;
    private final ExcelFileGenerator excelFileGenerator;

    /**
     * Exports data for the specified project and export type in the requested file format.
     *
     * @param projectId The ID of the project to export data for
     * @param exporterType The type of data to export (PROJECT, USER, TASK)
     * @param fileFormat The format of the file to generate (CSV, EXCEL)
     * @param compress Whether to compress the generated file
     * @return An ExportResponse containing the generated file as a resource
     */
    public ExportResponse exportData(Long projectId, ExporterType exporterType, FileFormat fileFormat, boolean compress) {
        log.info("Exporting data for project {} with type {} in {} format. Compression: {}",
                projectId, exporterType, fileFormat, compress);
        
        // Get the appropriate exporter using the factory
        AbstractExporter exporter = exporterFactory.getExporter(exporterType);
        
        // Get headers and data from the exporter
        String[] headers = exporter.getHeaders();
        List<Object[]> data = exporter.export(projectId);
        
        // Select the appropriate file generator strategy based on the requested format
        FileGeneratorStrategy fileGenerator = getFileGenerator(fileFormat);
        
        // Apply compression decorator if requested
        if (compress) {
            // Create a new CompressionDecorator instance with the current file generator
            CompressionDecorator decorator = new CompressionDecorator(fileGenerator);
            fileGenerator = decorator;
        }
        
        // Generate the file
        Resource resource = fileGenerator.generate(headers, data);
        
        // Create a base filename from the exporter type and project ID
        String baseName = exporterType.name().toLowerCase() + "_" + projectId;
        
        // Build and return the response
        return ExportResponse.builder()
                .resource(resource)
                .filename(fileGenerator.getFilename(baseName))
                .contentType(MediaType.parseMediaType(fileGenerator.getContentType()))
                .contentLength(getResourceLength(resource))
                .build();
    }
    
    /**
     * Returns the appropriate file generator strategy for the given file format.
     *
     * @param fileFormat The requested file format
     * @return The corresponding FileGeneratorStrategy
     */
    private FileGeneratorStrategy getFileGenerator(FileFormat fileFormat) {
        return switch (fileFormat) {
            case CSV -> csvFileGenerator;
            case EXCEL -> excelFileGenerator;
        };
    }
    
    /**
     * Gets the content length of a resource.
     *
     * @param resource The resource to get the length of
     * @return The content length in bytes
     */
    private long getResourceLength(Resource resource) {
        try {
            return resource.contentLength();
        } catch (Exception e) {
            log.warn("Could not determine resource length", e);
            return 0;
        }
    }
}
