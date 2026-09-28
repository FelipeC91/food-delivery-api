<<<<<<< Updated upstream:src/main/java/com/mypersonalportifolio/food_delivery_api/application/use_case/statistics/DailySalesProjectionDTO.java
package com.mypersonalportifolio.food_delivery_api.application.use_case.statistics;
=======
package com.mypersonalportifolio.food_delivery_api.application.statistic;
>>>>>>> Stashed changes:src/main/java/com/mypersonalportifolio/food_delivery_api/application/statistic/DailySalesProjectionDTO.java

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@AllArgsConstructor
public class DailySalesProjectionDTO {
    private Date creationDate;
    private Long totalSales;
    private BigDecimal totalBilled;
}
