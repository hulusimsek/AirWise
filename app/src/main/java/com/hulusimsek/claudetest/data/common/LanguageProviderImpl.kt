package com.hulusimsek.claudetest.data.common

import android.content.Context
import com.hulusimsek.claudetest.R
import com.hulusimsek.claudetest.domain.common.LanguageProvider
import java.io.File
import java.util.Locale
import java.util.zip.ZipFile
import javax.inject.Inject

class LanguageProviderImpl @Inject constructor(
    private val context: Context
) : LanguageProvider {

    override fun getSupportedLanguages(): List<String> {
        return context.resources.getStringArray(R.array.supported_languages).toList()
    }


    override fun getDeviceLanguage(): String {
        return Locale.getDefault().language
    }
}