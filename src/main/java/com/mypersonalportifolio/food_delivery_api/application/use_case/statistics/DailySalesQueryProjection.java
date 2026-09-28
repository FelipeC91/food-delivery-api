<<<<<<< Updated upstream:src/main/java/com/mypersonalportifolio/food_delivery_api/application/use_case/statistics/DailySalesQueryProjection.java
package com.mypersonalportifolio.food_delivery_api.application.use_case.statistics;
=======
package com.mypersonalportifolio.food_delivery_api.application.statistic;
>>>>>>> Stashed changes:src/main/java/com/mypersonalportifolio/food_delivery_api/application/statistic/DailySalesQueryProjection.java

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public interface DailySalesQueryProjection {

    record FilterDTO (
            UUID restaurantId,

            String offsetTimeZone,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime createdSince,

            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime createdUntil
    ){

        public FilterDTO(
                            UUID restaurantId,
                            String offsetTimeZone,
                        OffsetDateTime createdSince,
                         OffsetDateTime createdUntil) {

            var  OFFSET_UTC_PATTERN = Pattern.compile("^[+-](?:2[0-3]|[01][0-9]):[0-5][0-9]$");

            this.offsetTimeZone = offsetTimeZone != null && OFFSET_UTC_PATTERN.matcher(offsetTimeZone).matches() ? offsetTimeZone : "00:00";
            this.restaurantId = restaurantId;
            this.createdSince = createdSince;
            this.createdUntil = createdUntil;
        }
    }

    List<DailySalesProjectionDTO> retrieveDailySales(FilterDTO filterDTO);
}
