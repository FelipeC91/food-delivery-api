package com.mypersonalportifolio.food_delivery_api.domain.exception.statistics;

import java.util.List;

public interface ReportStatsService {

    byte[] generateDailySalesReportPdf(List<DailySalesProjectionDTO> dailySalesProjectionSource);
}
