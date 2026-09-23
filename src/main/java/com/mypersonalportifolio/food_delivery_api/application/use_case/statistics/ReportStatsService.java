package com.mypersonalportifolio.food_delivery_api.application.use_case.statistics;

import java.util.List;

public interface ReportStatsService {

    byte[] generateDailySalesReportPdf(List<DailySalesProjectionDTO> dailySalesProjectionSource);
}
