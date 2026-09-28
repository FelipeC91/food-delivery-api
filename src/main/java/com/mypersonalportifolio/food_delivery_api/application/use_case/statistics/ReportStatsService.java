<<<<<<< Updated upstream:src/main/java/com/mypersonalportifolio/food_delivery_api/application/use_case/statistics/ReportStatsService.java
package com.mypersonalportifolio.food_delivery_api.application.use_case.statistics;
=======
package com.mypersonalportifolio.food_delivery_api.application.statistic;
>>>>>>> Stashed changes:src/main/java/com/mypersonalportifolio/food_delivery_api/application/statistic/ReportStatsService.java

import java.util.List;

public interface ReportStatsService {

    byte[] generateDailySalesReportPdf(List<DailySalesProjectionDTO> dailySalesProjectionSource);
}
