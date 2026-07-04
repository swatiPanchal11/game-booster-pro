package com.xoun.gamebooster.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xoun.gamebooster.R
import com.xoun.gamebooster.utils.FavoriteManager
import com.xoun.gamebooster.utils.GameStatsManager
import com.xoun.gamebooster.utils.RamUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GamesDetailsActivity : AppCompatActivity() {

    private lateinit var txtAppName: TextView
    private lateinit var txtPackage: TextView
    private lateinit var txtRam: TextView
    private lateinit var btnLaunch: Button
    private lateinit var txtLaunchCount: TextView
    private lateinit var txtLastPlayed: TextView
    private lateinit var btnFavorite: Button
    private lateinit var txtFavStatus: TextView

    private var gamePackageName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_games_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtAppName    = findViewById(R.id.txtAppName)
        txtPackage    = findViewById(R.id.txtPackage)
        txtRam        = findViewById(R.id.txtRam)
        btnLaunch     = findViewById(R.id.btnLaunch)
        txtLaunchCount= findViewById(R.id.txtLaunchCount)
        txtLastPlayed = findViewById(R.id.txtLastPlayed)
        btnFavorite   = findViewById(R.id.btnFavorite)
        txtFavStatus  = findViewById(R.id.txtFavStatus)

        val appName = intent.getStringExtra("app_name") ?: ""
        gamePackageName = intent.getStringExtra("package_name") ?: ""

        txtAppName.text = appName
        txtPackage.text = gamePackageName
        txtRam.text     = "${RamUtils.getAvailableRam(this)} MB"

        // Launch count — show number only (label is in XML)
        txtLaunchCount.text = GameStatsManager.getLaunchCount(this, gamePackageName).toString()

        // Last played
        val lastLaunch = GameStatsManager.getLastLaunch(this, gamePackageName)
        txtLastPlayed.text = if (lastLaunch == 0L) {
            "Never played"
        } else {
            SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(lastLaunch))
        }

        updateFavoriteUI()

        btnFavorite.setOnClickListener {
            val current = FavoriteManager.isFavorite(this, gamePackageName)
            FavoriteManager.setFavorite(this, gamePackageName, !current)
            updateFavoriteUI()
        }

        btnLaunch.setOnClickListener {
            startActivity(
                Intent(this, BoostActivity::class.java)
                    .putExtra("package_name", gamePackageName)
            )
        }
    }

    private fun updateFavoriteUI() {
        val isFav = FavoriteManager.isFavorite(this, gamePackageName)
        btnFavorite.text = if (isFav) "★  REMOVE FAVORITE" else "★  ADD TO FAVORITES"
        txtFavStatus.text = if (isFav) "Saved" else "—"
    }
}
