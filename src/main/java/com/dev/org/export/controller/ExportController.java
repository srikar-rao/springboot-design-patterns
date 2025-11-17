package com.dev.org.export.controller;

import com.dev.org.export.model.ExportResponse;
import com.dev.org.export.model.ExporterType;
import com.dev.org.export.model.FileFormat;
import com.dev.org.export.service.ExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling export requests.
 * Provides an API endpoint for exporting data in different formats.
 */
@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
@Slf4j
public class ExportController {

    private final ExportService exportService;

    /**
     * Endpoint for exporting project data in the requested format.
     *
     * @param projectId The ID of the project to export data for
     * @param exportType The type of data to export (PROJECT, USER, TASK)
     * @param fileFormat The format of the file to generate (CSV, EXCEL), defaults to CSV
     * @param compress Whether to compress the generated file, defaults to false
     * @return A ResponseEntity containing the generated file as a downloadable resource
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<Resource> exportData(
            @PathVariable Long projectId,
            @RequestParam ExporterType exportType,
            @RequestParam(defaultValue = "CSV") FileFormat fileFormat,
            @RequestParam(defaultValue = "false") boolean compress) {
        
        log.info("Export request received for project {} with type {} in {} format. Compression: {}",
                projectId, exportType, fileFormat, compress);
        
        ExportResponse response = exportService.exportData(projectId, exportType, fileFormat, compress);
        
        log.info("Export completed successfully. Filename: {}", response.getFilename());
        
        return response.toResponseEntity();
    }
}
