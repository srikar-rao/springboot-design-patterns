package com.dev.org.export.decorator;

import com.dev.org.export.strategy.FileGeneratorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Decorator that adds compression functionality to a FileGeneratorStrategy.
 * Compresses the generated file using GZIP compression.
 */
@Component
public class CompressionDecorator extends FileGeneratorDecorator {

    /**
     * Constructor that takes a FileGeneratorStrategy to decorate.
     * This constructor is used by Spring when autowiring this decorator.
     */
    @Autowired
    public CompressionDecorator() {
        // Default constructor with null decorated object
        // The actual decorated object will be set when the decorator is used
        super(null);
    }
    
    /**
     * Constructor that takes a FileGeneratorStrategy to decorate.
     *
     * @param decorated The FileGeneratorStrategy to be decorated
     */
    public CompressionDecorator(FileGeneratorStrategy decorated) {
        super(decorated);
    }

    @Override
    public Resource generate(String[] headers, List<Object[]> data) {
        // Get the resource from the decorated generator
        Resource originalResource = decorated.generate(headers, data);
        
        try {
            // Read the original resource content
            byte[] originalContent = originalResource.getInputStream().readAllBytes();
            
            // Compress the content
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
                gzipStream.write(originalContent);
            }
            
            // Return the compressed content as a new resource
            return new ByteArrayResource(byteStream.toByteArray());
            
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to compress file", e);
        }
    }

    @Override
    public String getFilename(String baseName) {
        return decorated.getFilename(baseName) + ".gz";
    }

    @Override
    public String getContentType() {
        return "application/gzip";
    }
}
