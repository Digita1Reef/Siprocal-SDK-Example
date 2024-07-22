package com.siprocal.sdkexample

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

class Utils {
    companion object {
        var COLOR_GREEN: ForegroundColorSpan = ForegroundColorSpan(Color.parseColor("#00FF00"))
        fun setTexColoForConsole(txt: String, color: ForegroundColorSpan): SpannableString {
            val spannable = SpannableString(txt)
            spannable.setSpan(color, 0, txt.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return spannable
        }
    }
}