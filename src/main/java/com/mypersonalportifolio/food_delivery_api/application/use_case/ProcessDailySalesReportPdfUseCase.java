package com.mypersonalportifolio.food_delivery_api.application.use_case;

import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesFilterDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.concept.UseCase;
import com.mypersonalportifolio.food_delivery_api.application.analytics.DailySalesQueryProjection;
import com.mypersonalportifolio.food_delivery_api.application.analytics.ReportStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessDailySalesReportPdfUseCase implements UseCase<DailySalesFilterDTO, byte[]> {


    private final DailySalesQueryProjection dailySalesQueryProjection;
    private final ReportStatsService  reportStatsService;

    public ProcessDailySalesReportPdfUseCase(DailySalesQueryProjection dailySalesQueryProjection, ReportStatsService reportStatsService) {
        this.dailySalesQueryProjection = dailySalesQueryProjection;
        this.reportStatsService = reportStatsService;
    }

    @Override
    public byte[] execute(DailySalesFilterDTO filter) {
        var dailySalesDataSource= dailySalesQueryProjection.retrieveDailySales(filter);

        return reportStatsService.generateDailySalesReportPdf(dailySalesDataSource);
    }
}
