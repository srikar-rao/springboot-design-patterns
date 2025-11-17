package com.dev.org.export.template;

import com.dev.org.export.model.ExporterType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of AbstractExporter for User data.
 * Responsible for exporting user-related data for a project.
 */
@Component
public class UserExporter extends AbstractExporter {

    @Override
    public ExporterType getExporterType() {
        return ExporterType.USER;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {
            "User ID", 
            "Username", 
            "Full Name", 
            "Email", 
            "Role", 
            "Department", 
            "Join Date"
        };
    }

    @Override
    protected List<Object[]> getDataStream(Long projectId) {
        // In a real application, this would fetch data from a repository
        // For demonstration purposes, we'll generate sample data
        List<Object[]> data = new ArrayList<>();
        
        // Sample user data (in a real app, this would come from a database)
        data.add(new Object[] {
            1001L,
            "jdoe",
            "John Doe",
            "john.doe@example.com",
            "Project Manager",
            "Engineering",
            "2024-01-15"
        });
        
        data.add(new Object[] {
            1002L,
            "jsmith",
            "Jane Smith",
            "jane.smith@example.com",
            "Developer",
            "Engineering",
            "2024-02-01"
        });
        
        data.add(new Object[] {
            1003L,
            "rjohnson",
            "Robert Johnson",
            "robert.johnson@example.com",
            "QA Engineer",
            "Quality Assurance",
            "2024-02-15"
        });
        
        data.add(new Object[] {
            1004L,
            "mwilliams",
            "Mary Williams",
            "mary.williams@example.com",
            "Business Analyst",
            "Business",
            "2024-03-01"
        });
        
        return data;
    }
}
