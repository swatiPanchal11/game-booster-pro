package com.xoun.gamebooster.utils

import android.content.Context

object GameStatsManager {

    private const val PREF_NAME = "game_stats"

    fun increaseLaunchCount(
        context: Context,
        packageName: String
    ) {

        val prefs =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val currentCount =
            prefs.getInt(
                "${packageName}_count",
                0
            )

        prefs.edit()
            .putInt(
                "${packageName}_count",
                currentCount + 1
            )
            .apply()
    }

    fun getLaunchCount(
        context: Context,
        packageName: String
    ): Int {

        return context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .getInt(
                "${packageName}_count",
                0
            )
    }

    fun saveLastLaunch(
        context: Context,
        packageName: String
    ) {

        context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putLong(
                "${packageName}_last_launch",
                System.currentTimeMillis()
            )
            .apply()
    }

    fun getLastLaunch(
        context: Context,
        packageName: String
    ): Long {

        return context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .getLong(
                "${packageName}_last_launch",
                0L
            )
    }

    fun getAllStats(
        context: Context
    ): Map<String, Int> {

        val prefs =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        val result =
            mutableMapOf<String, Int>()

        prefs.all.forEach { entry ->

            if (entry.key.endsWith("_count")) {

                val packageName =
                    entry.key.replace(
                        "_count",
                        ""
                    )

                result[packageName] =
                    entry.value as Int
            }
        }

        return result
    }

    fun getTotalLaunches(
        context: Context
    ): Int {

        return getAllStats(context)
            .values
            .sum()
    }

    fun getMostPlayedGame(
        context: Context
    ): String? {

        val stats =
            getAllStats(context)

        return stats.maxByOrNull {
            it.value
        }?.key
    }

    fun getMostRecentGame(
        context: Context
    ): String? {

        val prefs =
            context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )

        var latestPackage: String? = null
        var latestTime = 0L

        prefs.all.forEach { entry ->

            if (entry.key.endsWith("_last_launch")) {

                val time =
                    entry.value as Long

                if (time > latestTime) {

                    latestTime = time

                    latestPackage =
                        entry.key.replace(
                            "_last_launch",
                            ""
                        )
                }
            }
        }

        return latestPackage
    }
}