package com.xoun.gamebooster.utils

import android.content.Context

object FavoriteManager {

    private const val PREF_NAME = "favorites"

    fun setFavorite(
        context: Context,
        packageName: String,
        favorite: Boolean
    ) {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        ).edit()
            .putBoolean(
                packageName,
                favorite
            )
            .apply()
    }

    fun isFavorite(
        context: Context,
        packageName: String
    ): Boolean {

        return context
            .getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            .getBoolean(
                packageName,
                false
            )
    }
}