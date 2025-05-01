package com.hulusimsek.claudetest.domain.common


interface ResourceProvider {
    fun getString(resId: Int): String
}