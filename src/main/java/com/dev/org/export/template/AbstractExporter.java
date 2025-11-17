package com.dev.org.export.template;

import com.dev.org.export.model.ExporterType;

import java.util.List;

/**
 * Abstract template class that defines the workflow for all export operations.
 * Implements the Template Method pattern by defining the skeleton of the export algorithm,
 * deferring some steps to subclasses.
 */
public abstract class AbstractExporter {

    /**
     * Template method that defines the export workflow.
     * This method orchestrates the export process by calling the abstract methods
     * that subclasses must implement.
     *
     * @param projectId The ID of the project to export data for
     * @return A list of data rows for export, where each row is an array of objects
     */
    public final List<Object[]> export(Long projectId) {
        // Template method pattern: defines the algorithm structure
        return getDataStream(projectId);
    }

    /**
     * Returns the type of exporter.
     * Each concrete exporter must specify which ExporterType it supports.
     *
     * @return The ExporterType supported by this exporter
     */
    public abstract ExporterType getExporterType();

    /**
     * Defines the column headers for the export.
     *
     * @return An array of strings representing column headers
     */
    public abstract String[] getHeaders();

    /**
     * Retrieves the data to be exported.
     * Each concrete exporter must implement this method to fetch and format
     * the appropriate data for its entity type.
     *
     * @param projectId The ID of the project to export data for
     * @return A list of data rows, where each row is an array of objects
     */
    protected abstract List<Object[]> getDataStream(Long projectId);
}
