package com.mypersonalportifolio.food_delivery_api.application.analytics;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public interface DailySalesQueryProjection {

    List<DailySalesProjectionDTO> retrieveDailySales(DailySalesFilterDTO filter);
}
