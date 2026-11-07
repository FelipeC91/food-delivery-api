package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesQueryProjection;
import com.mypersonalportifolio.food_delivery_api.application.analytics.ReportStatsService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessDailySalesReportPdfUseCaseTest {

    @Test
    void generatesPdfFromSalesForTheGivenFilter() {
        var projection = mock(DailySalesQueryProjection.class);
        var reportStatsService = mock(ReportStatsService.class);
        var useCase = new ProcessDailySalesReportPdfUseCase(projection, reportStatsService);
        var filter = new DailySalesFilterDTO(null, "00:00", null, null);
        var sales = List.of(mock(DailySalesProjectionDTO.class));
        var pdf = new byte[]{1, 2, 3};
        when(projection.retrieveDailySales(filter)).thenReturn(sales);
        when(reportStatsService.generateDailySalesReportPdf(sales)).thenReturn(pdf);

        var result = useCase.execute(filter);

        assertSame(pdf, result);
        verify(projection).retrieveDailySales(filter);
        verify(reportStatsService).generateDailySalesReportPdf(sales);
    }
}
