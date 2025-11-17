package com.dev.org.export.template;

import com.dev.org.export.model.ExporterType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of AbstractExporter for Project data.
 * Responsible for exporting project-related data.
 */
@Component
public class ProjectExporter extends AbstractExporter {

    @Override
    public ExporterType getExporterType() {
        return ExporterType.PROJECT;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {
            "Project ID", 
            "Project Name", 
            "Description", 
            "Start Date", 
            "End Date", 
            "Status", 
            "Owner"
        };
    }

    @Override
    protected List<Object[]> getDataStream(Long projectId) {
        // In a real application, this would fetch data from a repository
        // For demonstration purposes, we'll generate sample data
        List<Object[]> data = new ArrayList<>();
        
        // Sample project data (in a real app, this would come from a database)
        data.add(new Object[] {
            projectId,
            "Sample Project " + projectId,
            "This is a sample project for demonstration",
            "2025-01-01",
            "2025-12-31",
            "ACTIVE",
            "John Doe"
        });
        
        // Add some mock related projects
        for (int i = 1; i <= 3; i++) {
            data.add(new Object[] {
                projectId + i,
                "Related Project " + (projectId + i),
                "This is a related project " + i,
                "2025-02-0" + i,
                "2025-11-" + (20 + i),
                i % 2 == 0 ? "ACTIVE" : "PLANNING",
                "Jane Smith"
            });
        }
        
        return data;
    }
}
