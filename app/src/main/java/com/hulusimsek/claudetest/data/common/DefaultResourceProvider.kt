package com.hulusimsek.claudetest.data.common

import android.content.Context
import androidx.annotation.StringRes
import com.hulusimsek.claudetest.domain.common.ResourceProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
): ResourceProvider {
    override fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }
}