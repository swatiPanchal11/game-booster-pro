package com.xoun.gamebooster.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.xoun.gamebooster.models.GameModel

object GameScanner {

    fun getInstalledGames(context: Context): List<GameModel> {

        val pm = context.packageManager

        val gameList = mutableListOf<GameModel>()

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val apps = pm.queryIntentActivities(intent, 0)

        for (app in apps) {

            Log.d(
                "GAME_BOOSTER",
                "Found App: ${app.loadLabel(pm)}"
            )

            gameList.add(
                GameModel(
                    appName = app.loadLabel(pm).toString(),
                    packageName = app.activityInfo.packageName,
                    icon = app.loadIcon(pm)
                )
            )
        }

        Log.d(
            "GAME_BOOSTER",
            "Total Apps Found = ${gameList.size}"
        )

        return gameList.sortedBy { it.appName }
    }
}