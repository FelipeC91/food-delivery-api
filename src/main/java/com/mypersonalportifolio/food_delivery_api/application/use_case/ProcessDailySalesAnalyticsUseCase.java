package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesQueryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessDailySalesAnalyticsUseCase implements UseCase<DailySalesFilterDTO, List<DailySalesProjectionDTO>> {

    private final DailySalesQueryProjection dailySalesQueryProjection;

    public ProcessDailySalesAnalyticsUseCase(DailySalesQueryProjection dailySalesQueryProjection) {
        this.dailySalesQueryProjection = dailySalesQueryProjection;
    }

    @Override
    public List<DailySalesProjectionDTO> execute(DailySalesFilterDTO filter) {
        return dailySalesQueryProjection.retrieveDailySales(filter);
    }
}
