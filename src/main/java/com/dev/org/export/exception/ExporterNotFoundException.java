package com.dev.org.export.exception;

import com.dev.org.export.model.ExporterType;

/**
 * Exception thrown when a requested exporter type is not found.
 * This occurs when the ExporterFactory cannot resolve an exporter for the given type.
 */
public class ExporterNotFoundException extends RuntimeException {
    
    public ExporterNotFoundException(ExporterType type) {
        super("No exporter found for type: " + type);
    }
    
    public ExporterNotFoundException(String message) {
        super(message);
    }
}
