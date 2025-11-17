package com.dev.org.export.decorator;

import com.dev.org.export.strategy.FileGeneratorStrategy;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Base decorator class for FileGeneratorStrategy.
 * Implements the Decorator pattern to allow dynamic extension of file generator functionality.
 */
public abstract class FileGeneratorDecorator implements FileGeneratorStrategy {

    protected final FileGeneratorStrategy decorated;

    /**
     * Constructor that takes a FileGeneratorStrategy to decorate.
     *
     * @param decorated The FileGeneratorStrategy to be decorated
     */
    protected FileGeneratorDecorator(FileGeneratorStrategy decorated) {
        this.decorated = decorated;
    }

    @Override
    public Resource generate(String[] headers, List<Object[]> data) {
        // Default implementation delegates to the decorated object
        return decorated.generate(headers, data);
    }

    @Override
    public String getFilename(String baseName) {
        // Default implementation delegates to the decorated object
        return decorated.getFilename(baseName);
    }

    @Override
    public String getContentType() {
        // Default implementation delegates to the decorated object
        return decorated.getContentType();
    }
}
