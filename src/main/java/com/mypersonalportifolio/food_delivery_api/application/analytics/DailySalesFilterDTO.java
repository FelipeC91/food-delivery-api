package com.mypersonalportifolio.food_delivery_api.application.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@AllArgsConstructor
@Setter
@Getter
public class DailySalesFilterDTO {

    private UUID restaurantId;

    private String offsetTimeZone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdSince;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdUntil;

    public String getOffsetTimeZone() {
        var  OFFSET_UTC_PATTERN = Pattern.compile("^[+-](?:2[0-3]|[01][0-9]):[0-5][0-9]$");

        return this.offsetTimeZone  != null && OFFSET_UTC_PATTERN.matcher(offsetTimeZone).matches() ? offsetTimeZone : "00:00";
    }
}

