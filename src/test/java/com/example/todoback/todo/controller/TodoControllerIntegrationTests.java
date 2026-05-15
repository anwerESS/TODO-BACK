package com.example.todoback.todo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllReturnsInitialTodos() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Review Angular routing"))
                .andExpect(jsonPath("$[0].priority").value("HIGH"))
                .andExpect(jsonPath("$[1].completed").value(true))
                .andExpect(jsonPath("$[4].category").value("Debug"));
    }

    @Test
    void createReturnsCreatedTodoAfterSeedData() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": " Connect Angular service ",
                                  "description": "Use HttpClient instead of localStorage",
                                  "priority": "HIGH",
                                  "category": "API"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/todos/6"))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.title").value("Connect Angular service"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void patchUpdatesTodo() throws Exception {
        mockMvc.perform(patch("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Review API contract",
                                  "completed": true,
                                  "priority": "MEDIUM",
                                  "description": ""
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Review API contract"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.priority").value("MEDIUM"))
                .andExpect(jsonPath("$.description").doesNotExist());
    }

    @Test
    void deleteCompletedReturnsRemovedCount() throws Exception {
        mockMvc.perform(delete("/api/todos/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.removedCount").value(1));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void invalidCreateReturnsValidationErrors() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "   ",
                                  "priority": null
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.validationErrors.title").exists())
                .andExpect(jsonPath("$.validationErrors.priority").exists());
    }

    @Test
    void unknownTodoReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo with id 999 was not found"));
    }
}
