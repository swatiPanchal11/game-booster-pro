package com.xoun.gamebooster.utils

import android.content.Context

object GamePrefs {

    private const val PREF_NAME = "game_booster_pref"
    private const val KEY_GAMES = "selected_games"

    fun saveGames(
        context: Context,
        packageNames: Set<String>
    ) {

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putStringSet(
                KEY_GAMES,
                packageNames
            )
            .apply()
    }

    fun getGames(
        context: Context
    ): Set<String> {

        return context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .getStringSet(
                KEY_GAMES,
                emptySet()
            ) ?: emptySet()
    }
}