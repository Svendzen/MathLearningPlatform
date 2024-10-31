package org.svendzen.contentservice.controllers;

import org.junit.jupiter.api.Test;
import org.svendzen.contentservice.models.MathProblem;
import org.svendzen.contentservice.models.MathProblemType;
import org.svendzen.contentservice.services.MathProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MathProblemController.class)
class MathProblemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathProblemService mathProblemService;

    @Test
    void testGenerateProblem() throws Exception {
        // Create a sample MathProblem to be returned by the service
        MathProblem mockProblem = new MathProblem();
        mockProblem.setQuestion("5 + 3");
        mockProblem.setAnswer(8);
        mockProblem.setType(MathProblemType.ADDITION);

        // Mock the service method
        when(mathProblemService.generateProblem(MathProblemType.ADDITION)).thenReturn(mockProblem);

        // Perform the request and verify the result
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mathproblem/generate-problem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"ADDITION\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is("5 + 3")))
                .andExpect(jsonPath("$.answer", is(8)))
                .andExpect(jsonPath("$.type", is("ADDITION")));

        // Verify that the service method was called once with the specified type
        verify(mathProblemService, times(1)).generateProblem(MathProblemType.ADDITION);
    }
}
