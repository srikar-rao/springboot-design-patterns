package com.dev.org.export.factory;

import com.dev.org.export.exception.ExporterNotFoundException;
import com.dev.org.export.model.ExporterType;
import com.dev.org.export.template.AbstractExporter;
import com.dev.org.export.template.ProjectExporter;
import com.dev.org.export.template.TaskExporter;
import com.dev.org.export.template.UserExporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExporterFactoryTest {

    @Mock
    private ProjectExporter projectExporter;

    @Mock
    private UserExporter userExporter;

    @Mock
    private TaskExporter taskExporter;

    private ExporterFactory exporterFactory;

    @BeforeEach
    void setUp() {
        // Configure mock exporters
        when(projectExporter.getExporterType()).thenReturn(ExporterType.PROJECT);
        when(userExporter.getExporterType()).thenReturn(ExporterType.USER);
        when(taskExporter.getExporterType()).thenReturn(ExporterType.TASK);

        // Create list of exporters
        List<AbstractExporter> exporters = Arrays.asList(
                projectExporter,
                userExporter,
                taskExporter
        );

        // Initialize factory with exporters
        exporterFactory = new ExporterFactory(exporters);
    }

    @Test
    @DisplayName("Should return ProjectExporter when PROJECT type is requested")
    void shouldReturnProjectExporterWhenProjectTypeRequested() {
        // When
        AbstractExporter exporter = exporterFactory.getExporter(ExporterType.PROJECT);

        // Then
        assertNotNull(exporter);
        assertEquals(projectExporter, exporter);
    }

    @Test
    @DisplayName("Should return UserExporter when USER type is requested")
    void shouldReturnUserExporterWhenUserTypeRequested() {
        // When
        AbstractExporter exporter = exporterFactory.getExporter(ExporterType.USER);

        // Then
        assertNotNull(exporter);
        assertEquals(userExporter, exporter);
    }

    @Test
    @DisplayName("Should return TaskExporter when TASK type is requested")
    void shouldReturnTaskExporterWhenTaskTypeRequested() {
        // When
        AbstractExporter exporter = exporterFactory.getExporter(ExporterType.TASK);

        // Then
        assertNotNull(exporter);
        assertEquals(taskExporter, exporter);
    }

    @Test
    @DisplayName("Should throw ExporterNotFoundException when exporter not found")
    void shouldThrowExporterNotFoundExceptionWhenExporterNotFound() {
        // Given a factory with no exporters
        exporterFactory = new ExporterFactory(List.of());

        // When/Then
        assertThrows(ExporterNotFoundException.class, () -> {
            exporterFactory.getExporter(ExporterType.PROJECT);
        });
    }
}
