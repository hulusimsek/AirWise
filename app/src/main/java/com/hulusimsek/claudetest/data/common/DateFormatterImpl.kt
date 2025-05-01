package com.hulusimsek.claudetest.data.common

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hulusimsek.claudetest.domain.common.DateFormatter
import com.hulusimsek.claudetest.domain.common.LanguageProvider
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(
    private val languageProvider: LanguageProvider
) : DateFormatter {
    override fun formatToTurkishDayMonth(dateTimeString: String): String {
        val deviceLang = languageProvider.getDeviceLanguage()
        val supported = languageProvider.getSupportedLanguages()
        val lang = if (deviceLang in supported) deviceLang else "en"
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatWithJava8Api(dateTimeString, lang)
        } else {
            formatWithLegacyApi(dateTimeString, lang)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatWithJava8Api(dateTimeString: String, lang: String): String {
        val inputFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = java.time.LocalDateTime.parse(dateTimeString, inputFormatter)
        lateinit var outputFormatter: java.time.format.DateTimeFormatter

        val locale = java.util.Locale(lang)
        outputFormatter = if(lang == "tr") {
            java.time.format.DateTimeFormatter
                .ofPattern("EEEE, d MMMM")
                .withLocale(locale)
        } else {
            java.time.format.DateTimeFormatter
                .ofPattern("EEEE, MMMM d")
                .withLocale(locale)
        }


        return dateTime.format(outputFormatter)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    }

    private fun formatWithLegacyApi(dateTimeString: String, lang: String): String {
        val locale = java.util.Locale(lang)
        val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
        var outputFormat = java.text.SimpleDateFormat("EEEE, d MMMM", locale)

        val date = inputFormat.parse(dateTimeString) ?: return ""
        if(lang != "tr")
            outputFormat = java.text.SimpleDateFormat("EEEE, MMMM d", locale)


        val formattedDate = outputFormat.format(date)

        return formattedDate.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(locale) else it.toString()
        }
    }
}
