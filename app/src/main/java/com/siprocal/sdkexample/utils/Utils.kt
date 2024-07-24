package com.siprocal.sdkexample.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    companion object {
        const val DATE_FORMAT = "MM/dd/yyyy"
        const val DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss"
        var COLOR_GREEN: ForegroundColorSpan = ForegroundColorSpan(Color.parseColor("#00FF00"))
        fun setTexColoForConsole(txt: String, color: ForegroundColorSpan): SpannableString {
            val spannable = SpannableString(txt)
            spannable.setSpan(color, 0, txt.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannable
        }
        fun formatTimestamp(timestamp: Long, formatString: String): String {
            val date = Date(timestamp)
            val format = SimpleDateFormat(formatString, Locale.getDefault())
            return format.format(date)
        }
    }
}