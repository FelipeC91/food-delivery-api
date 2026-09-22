package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesQueryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessDailySalesStatsUseCase implements UseCase<DailySalesQueryProjection.FilterDTO, List<DailySalesProjectionDTO>> {

    @Autowired
    DailySalesQueryProjection dailySalesQueryProjection;

    @Override
    public List<DailySalesProjectionDTO> execute(DailySalesQueryProjection.FilterDTO filter) {
        return dailySalesQueryProjection.retrieveDailySales(filter);
    }
}
