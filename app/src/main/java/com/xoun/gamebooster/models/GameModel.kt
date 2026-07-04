package com.xoun.gamebooster.models

import android.graphics.drawable.Drawable

data class GameModel(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
    var isSelected: Boolean = false,
    var isFavorite: Boolean = false
)