package com.xoun.gamebooster.utils

import android.os.Environment
import android.os.StatFs

object StorageUtils {

    fun getTotalStorageGB(): Long {

        val stat = StatFs(
            Environment.getDataDirectory().path
        )

        val totalBytes =
            stat.blockCountLong * stat.blockSizeLong

        return totalBytes / (1024 * 1024 * 1024)
    }

    fun getFreeStorageGB(): Long {

        val stat = StatFs(
            Environment.getDataDirectory().path
        )

        val freeBytes =
            stat.availableBlocksLong * stat.blockSizeLong

        return freeBytes / (1024 * 1024 * 1024)
    }

    fun getUsedStorageGB(): Long {

        return getTotalStorageGB() - getFreeStorageGB()
    }
}