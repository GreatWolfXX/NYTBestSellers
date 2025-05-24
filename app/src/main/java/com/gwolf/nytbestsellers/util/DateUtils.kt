package com.gwolf.nytbestsellers.util

import java.time.DayOfWeek
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

object DateUtils {

    private const val HOUR_UPDATE = 19
    private const val MINUTE_UPDATE = 30

    fun shouldUpdateData(lastUpdateDateString: String): Boolean {
        val lastUpdateDate = ZonedDateTime.parse(lastUpdateDateString)
        val etZone = ZoneId.of("America/New_York")

        val newDateUpdate = lastUpdateDate
            .with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))
            .withHour(HOUR_UPDATE)
            .withMinute(MINUTE_UPDATE)

        val nowET = ZonedDateTime.now(etZone)

        val result = nowET.isAfter(newDateUpdate)
        return result
    }

    fun getCurrentDateNY(): String {
        val etZone = ZoneId.of("America/New_York")

        val nowET = ZonedDateTime.now(etZone).toString()

        return nowET
    }
}