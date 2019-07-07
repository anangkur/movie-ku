package com.anangkur.madesubmission1.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.util.*

object Utils{
    fun nomalizeRating(oldValue: Float): Float{
        return ((oldValue-0)/10-0)*((5-0)+0)
    }

    fun setLocale(lang: String, context: Context) {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(lang.toLowerCase()))
        res.updateConfiguration(conf, dm)
    }

    fun createCircularProgressDrawable(context: Context): CircularProgressDrawable{
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 4f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}