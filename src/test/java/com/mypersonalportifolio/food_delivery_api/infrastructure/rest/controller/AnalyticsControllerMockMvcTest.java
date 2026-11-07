package com.mypersonalportifolio.food_delivery_api.infrastructure.rest.controller;

import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesAnalyticsUseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.ProcessDailySalesReportPdfUseCase;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnalyticsControllerMockMvcTest extends MockMvcTestSupport {

    @Test
    void handlesDiscoverabilityRequest() throws Exception {
        var controller = new AnalyticsController(mock(ProcessDailySalesAnalyticsUseCase.class),
                mock(ProcessDailySalesReportPdfUseCase.class));

        mockMvc(controller).perform(get("/analytics/")).andExpect(status().isOk());
    }
}
