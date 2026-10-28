package com.mypersonalportifolio.food_delivery_api.application.analytics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class DailySalesFilterDTO {

    @Setter
    private UUID restaurantId;

    private final String offsetTimeZone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime createdSince;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime createdUntil;

    public DailySalesFilterDTO(String offsetTimeZone, OffsetDateTime createdSince, OffsetDateTime createdUntil) {
        var  OFFSET_UTC_PATTERN = Pattern.compile("^[+-](?:2[0-3]|[01][0-9]):[0-5][0-9]$");

        this.offsetTimeZone = offsetTimeZone != null && OFFSET_UTC_PATTERN.matcher(offsetTimeZone).matches() ? offsetTimeZone : "00:00";
        this.createdSince = createdSince;
        this.createdUntil = createdUntil;
    }
}

