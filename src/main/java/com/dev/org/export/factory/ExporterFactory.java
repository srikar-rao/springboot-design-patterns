package com.dev.org.export.factory;

import com.dev.org.export.exception.ExporterNotFoundException;
import com.dev.org.export.model.ExporterType;
import com.dev.org.export.template.AbstractExporter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory class responsible for providing the appropriate exporter implementation
 * based on the requested ExporterType.
 * Implements the Factory pattern to centralize exporter instance creation and selection.
 */
@Component
public class ExporterFactory {

    private final Map<ExporterType, AbstractExporter> exporterMap;

    /**
     * Constructor that automatically maps all available exporter implementations
     * to their corresponding ExporterType.
     *
     * @param exporters List of all AbstractExporter implementations available in the application context
     */
    public ExporterFactory(List<AbstractExporter> exporters) {
        // Create a map of ExporterType to AbstractExporter implementation
        this.exporterMap = exporters.stream()
                .collect(Collectors.toMap(
                        AbstractExporter::getExporterType,
                        Function.identity()
                ));
    }

    /**
     * Returns the appropriate exporter implementation for the given type.
     *
     * @param type The ExporterType to get an exporter for
     * @return The matching AbstractExporter implementation
     * @throws ExporterNotFoundException if no exporter is found for the given type
     */
    public AbstractExporter getExporter(ExporterType type) {
        AbstractExporter exporter = exporterMap.get(type);
        
        if (exporter == null) {
            throw new ExporterNotFoundException(type);
        }
        
        return exporter;
    }
}
