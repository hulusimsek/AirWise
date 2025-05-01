package com.hulusimsek.claudetest.domain.common

interface DateFormatter {
    fun formatToTurkishDayMonth(dateTimeString: String): String
}