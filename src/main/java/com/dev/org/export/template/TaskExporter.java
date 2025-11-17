package com.dev.org.export.template;

import com.dev.org.export.model.ExporterType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of AbstractExporter for Task data.
 * Responsible for exporting task-related data for a project.
 */
@Component
public class TaskExporter extends AbstractExporter {
    
    private static final String DATE_2025_01_15 = "2025-01-15";

    @Override
    public ExporterType getExporterType() {
        return ExporterType.TASK;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {
            "Task ID", 
            "Task Name", 
            "Description", 
            "Status", 
            "Priority", 
            "Assigned To", 
            "Due Date", 
            "Created Date"
        };
    }

    @Override
    protected List<Object[]> getDataStream(Long projectId) {
        // In a real application, this would fetch data from a repository
        // For demonstration purposes, we'll generate sample data
        List<Object[]> data = new ArrayList<>();
        
        // Sample task data (in a real app, this would come from a database)
        data.add(new Object[] {
            101L,
            "Setup Development Environment",
            "Install and configure all necessary tools and dependencies",
            "COMPLETED",
            "HIGH",
            "Jane Smith",
            "2025-01-10",
            "2025-01-01"
        });
        
        data.add(new Object[] {
            102L,
            "Design Database Schema",
            "Create ER diagrams and define table structures",
            "COMPLETED",
            "HIGH",
            "Robert Johnson",
            DATE_2025_01_15,
            "2025-01-05"
        });
        
        data.add(new Object[] {
            103L,
            "Implement User Authentication",
            "Develop login, registration, and password reset functionality",
            "IN_PROGRESS",
            "HIGH",
            "Jane Smith",
            "2025-01-25",
            "2025-01-10"
        });
        
        data.add(new Object[] {
            104L,
            "Create API Documentation",
            "Document all API endpoints using Swagger",
            "NOT_STARTED",
            "MEDIUM",
            "Mary Williams",
            "2025-02-05",
            DATE_2025_01_15
        });
        
        data.add(new Object[] {
            105L,
            "Write Unit Tests",
            "Achieve at least 80% code coverage",
            "NOT_STARTED",
            "MEDIUM",
            "Robert Johnson",
            "2025-02-10",
            DATE_2025_01_15
        });
        
        return data;
    }
}
