package com.hulusimsek.claudetest.domain.model

enum class WindDirection(val symbol: String) {
    N("⬆️"),
    NNE("⬆️"),
    NE("↗️"),
    ENE("↗️"),
    E("➡️"),
    ESE("↘️"),
    SE("↘️"),
    SSE("↘️"),
    S("⬇️"),
    SSW("⬇️"),
    SW("↙️"),
    WSW("↙️"),
    W("⬅️"),
    WNW("↖️"),
    NW("↖️"),
    NNW("↖️"),
    X("⚠️"); // Bilinmeyen yön

    companion object {
        fun fromString(value: String): WindDirection? {
            return entries.find { it.name == value }
        }
    }
}