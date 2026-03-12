package com.siprocal.sdkexample.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    const val DATE_FORMAT = "MM/dd/yyyy"
    const val DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss"

    val colorGreen = ForegroundColorSpan(Color.parseColor("#00FF00"))

    fun setTextColorForConsole(text: String, color: ForegroundColorSpan): SpannableString {
        val spannable = SpannableString(text)
        spannable.setSpan(color, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun formatTimestamp(timestamp: Long, formatString: String): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat(formatString, Locale.getDefault())
        return format.format(date)
    }
}
