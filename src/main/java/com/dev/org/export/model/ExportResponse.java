package com.dev.org.export.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Model class representing the response for an export operation.
 * Contains the exported file as a resource and metadata about the export.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportResponse {
    private Resource resource;
    private String filename;
    private MediaType contentType;
    private long contentLength;

    /**
     * Converts this ExportResponse to a Spring ResponseEntity for HTTP response.
     * 
     * @return ResponseEntity containing the resource and appropriate headers
     */
    public ResponseEntity<Resource> toResponseEntity() {
        return ResponseEntity.ok()
                .contentType(contentType)
                .contentLength(contentLength)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}
