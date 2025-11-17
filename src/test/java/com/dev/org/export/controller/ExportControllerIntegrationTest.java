package com.dev.org.export.controller;

import com.dev.org.export.model.ExporterType;
import com.dev.org.export.model.FileFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should export project data in CSV format")
    void shouldExportProjectDataInCsvFormat() throws Exception {
        // When
        MvcResult result = mockMvc.perform(get("/api/export/{projectId}", 123)
                .param("exportType", ExporterType.PROJECT.name())
                .param("fileFormat", FileFormat.CSV.name())
                .param("compress", "false"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", containsString("text/plain")))
                .andExpect(header().string("Content-Disposition", containsString("attachment; filename=\"project_123.csv\"")))
                .andReturn();

        // Then
        byte[] content = result.getResponse().getContentAsByteArray();
        assertTrue(content.length > 0);
        
        String csvContent = new String(content);
        assertTrue(csvContent.contains("Project ID"));
        assertTrue(csvContent.contains("Project Name"));
        assertTrue(csvContent.contains("Sample Project 123"));
    }

    @Test
    @DisplayName("Should export user data in Excel format")
    void shouldExportUserDataInExcelFormat() throws Exception {
        // When
        MvcResult result = mockMvc.perform(get("/api/export/{projectId}", 123)
                .param("exportType", ExporterType.USER.name())
                .param("fileFormat", FileFormat.EXCEL.name())
                .param("compress", "false"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", 
                        containsString("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")))
                .andExpect(header().string("Content-Disposition", containsString("attachment; filename=\"user_123.xlsx\"")))
                .andReturn();

        // Then
        byte[] content = result.getResponse().getContentAsByteArray();
        assertTrue(content.length > 0);
        
        // Excel file should start with PK (zip file signature)
        assertEquals('P', (char) content[0]);
        assertEquals('K', (char) content[1]);
    }

    @Test
    @DisplayName("Should export task data with compression")
    void shouldExportTaskDataWithCompression() throws Exception {
        // When
        MvcResult result = mockMvc.perform(get("/api/export/{projectId}", 123)
                .param("exportType", ExporterType.TASK.name())
                .param("fileFormat", FileFormat.CSV.name())
                .param("compress", "true"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", containsString("application/gzip")))
                .andExpect(header().string("Content-Disposition", containsString("attachment; filename=\"task_123.csv.gz\"")))
                .andReturn();

        // Then
        byte[] content = result.getResponse().getContentAsByteArray();
        assertTrue(content.length > 0);
        
        // GZIP magic number check
        assertEquals((byte) 0x1f, content[0]);
        assertEquals((byte) 0x8b, content[1]);
    }
}
