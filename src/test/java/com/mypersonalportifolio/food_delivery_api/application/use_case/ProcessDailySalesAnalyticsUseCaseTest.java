package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesQueryProjection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessDailySalesAnalyticsUseCaseTest {

    @Test
    void retrievesDailySalesForTheGivenFilter() {
        var projection = mock(DailySalesQueryProjection.class);
        var useCase = new ProcessDailySalesAnalyticsUseCase(projection);
        var filter = new DailySalesFilterDTO("00:00", null, null);
        var sales = List.of(mock(DailySalesProjectionDTO.class));
        when(projection.retrieveDailySales(filter)).thenReturn(sales);

        var result = useCase.execute(filter);

        assertSame(sales, result);
        verify(projection).retrieveDailySales(filter);
    }
}
