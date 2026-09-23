package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesQueryProjection;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.ReportStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessDailySalesReportPdfUseCase implements UseCase<DailySalesQueryProjection.FilterDTO, byte[]> {


    @Autowired
    DailySalesQueryProjection dailySalesQueryProjection;

    @Autowired
    ReportStatsService  reportStatsService;

    @Override
    public byte[] execute(DailySalesQueryProjection.FilterDTO filter) {
        var dailySalesDataSource= dailySalesQueryProjection.retrieveDailySales(filter);

        return reportStatsService.generateDailySalesReportPdf(dailySalesDataSource);
    }
}
