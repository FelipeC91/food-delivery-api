package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.exception_handler;

import com.mypersonalportifolio.food_delivery_api.domain.exception.BusinessConstraintsViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiGlobalExceptionHandlerTest {

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new TestController())
            .setControllerAdvice(new com.mypersonalportifolio.food_delivery_api.infrastructure.rest.exception.ApiGlobalExceptionHandler())
            .build();

    @Test
    void shouldReturnApiProblemDetailsForBusinessConstraintViolation() throws Exception {
        mockMvc.perform(get("/business-constraint").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("Constraint was violated"))
                .andExpect(jsonPath("$.httpStatusCode").value(400))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @RestController
    static class TestController {

        @GetMapping("/business-constraint")
        void triggerBusinessConstraintViolation() {
            throw new BusinessConstraintsViolationException("Constraint was violated");
        }
    }
}
