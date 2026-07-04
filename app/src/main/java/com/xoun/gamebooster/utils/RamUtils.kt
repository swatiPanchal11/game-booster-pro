package com.xoun.gamebooster.utils

import android.app.ActivityManager
import android.content.Context

object RamUtils {

    fun getAvailableRam(context: Context): Long {

        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE)
                    as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)

        return memoryInfo.availMem / (1024 * 1024)
    }

    fun getTotalRam(context: Context): Long {

        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE)
                    as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)

        return memoryInfo.totalMem / (1024 * 1024)
    }

    fun getUsedRam(context: Context): Long {

        return getTotalRam(context) - getAvailableRam(context)
    }
}