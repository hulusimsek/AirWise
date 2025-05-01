package com.hulusimsek.claudetest.domain.common

interface LanguageProvider {
    fun getSupportedLanguages(): List<String>
    fun getDeviceLanguage(): String
}