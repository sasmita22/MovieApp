package com.hiroshisasmita.core.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

enum class DateFormat(val format: String) {
    YYYY_MM_DD_DASH("yyyy-MM-dd"),
    UTC_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    DD_MMM_YYYY_HH_MM_A("dd MMM yyyy, hh:mm a"),
    DD_MMMM_YYYY("dd MMMM yyyy")
}

enum class LocaleEnum(val locale: Locale) {
    INDONESIA(Locale("id", "ID")),
    ENGLISH(Locale("en", "US"))
}

fun String.extConvertDateStringFormat(
    formatBefore: DateFormat,
    formatAfter: DateFormat,
    timeZoneBefore: TimeZone = TimeZone.getDefault(),
    timeZoneAfter: TimeZone = TimeZone.getDefault(),
    localeBefore: Locale = Locale.getDefault(),
    localeAfter: Locale = Locale.getDefault(),
): String {
    return try {
        SimpleDateFormat(formatBefore.format, localeBefore).run {
            this.timeZone = timeZoneBefore
            parse(this@extConvertDateStringFormat)
        }?.let { date ->
            val formatter = SimpleDateFormat(formatAfter.format, localeAfter)
            formatter.timeZone = timeZoneAfter
            formatter.format(date)
        } ?: "N/A"
    } catch (e: Exception) {
        "N/A"
    }
}