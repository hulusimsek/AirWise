package com.hulusimsek.claudetest.domain.usecase

import com.hulusimsek.claudetest.domain.common.DateFormatter
import javax.inject.Inject

class FormatDateUseCase @Inject constructor(
    private val dateFormatter: DateFormatter
) {
    operator fun invoke(dateTimeString: String): String {
        return dateFormatter.formatToTurkishDayMonth(dateTimeString)
    }
}