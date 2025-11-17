package com.dev.org.export.strategy;

import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Strategy interface for generating different file formats.
 * Implements the Strategy pattern to encapsulate different file generation algorithms.
 */
public interface FileGeneratorStrategy {
    
    /**
     * Generates a file resource from the provided headers and data.
     *
     * @param headers Column headers for the file
     * @param data Data rows to be included in the file
     * @return A Resource containing the generated file
     */
    Resource generate(String[] headers, List<Object[]> data);
    
    /**
     * Returns the filename with appropriate extension for this file format.
     *
     * @param baseName The base name for the file (without extension)
     * @return The complete filename with extension
     */
    String getFilename(String baseName);
    
    /**
     * Returns the MIME type for the generated file.
     *
     * @return The MIME type as a string
     */
    String getContentType();
}
