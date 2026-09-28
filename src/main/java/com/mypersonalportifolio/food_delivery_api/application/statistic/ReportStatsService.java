package com.mypersonalportifolio.food_delivery_api.application.statistic;

import java.util.List;

public interface ReportStatsService {

    byte[] generateDailySalesReportPdf(List<DailySalesProjectionDTO> dailySalesProjectionSource);
}
