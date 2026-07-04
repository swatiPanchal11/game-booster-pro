package com.xoun.gamebooster.utils

import android.content.Context

object HealthUtils {

    fun getHealthScore(context: Context): Int {

        val totalRam = RamUtils.getTotalRam(context)
        val freeRam = RamUtils.getAvailableRam(context)

        val ramPercentage =
            ((freeRam.toFloat() / totalRam.toFloat()) * 100).toInt()

        return ramPercentage.coerceIn(0, 100)
    }
}