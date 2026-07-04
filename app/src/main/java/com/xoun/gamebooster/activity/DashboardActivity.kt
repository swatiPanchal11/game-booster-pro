package com.xoun.gamebooster.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xoun.gamebooster.R
import com.xoun.gamebooster.utils.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var txtTotalGames: TextView
    private lateinit var txtTotalLaunches: TextView
    private lateinit var txtMostPlayed: TextView
    private lateinit var txtRecentGame: TextView
    private lateinit var txtFavorites: TextView
    private lateinit var txtHealth: TextView
    private lateinit var txtRam: TextView
    private lateinit var txtStorage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadDashboard()
    }

    private fun loadDashboard() {

        val context = this

        // 1. Selected games only (CORE DATASET)
        val selectedPackages = GamePrefs.getGames(context)

        val allInstalledGames = GameScanner.getInstalledGames(context)

        val selectedGames = allInstalledGames.filter {
            selectedPackages.contains(it.packageName)
        }

        // 2. Stats
        val totalGames = selectedGames.size
        val totalLaunches = GameStatsManager.getTotalLaunches(context)
        val mostPlayed = GameStatsManager.getMostPlayedGame(context)
        val recent = GameStatsManager.getMostRecentGame(context)

        // 3. Favorites (ONLY selected games)
        val favorites = selectedGames.count {
            FavoriteManager.isFavorite(context, it.packageName)
        }

        // 4. System metrics
        val health = HealthUtils.getHealthScore(context)
        val ram = RamUtils.getAvailableRam(context)
        val storage = StorageUtils.getFreeStorageGB()

        // 5. UI binding
        txtTotalGames.text = "Total Games: $totalGames"
        txtTotalLaunches.text = "$totalLaunches"
        txtMostPlayed.text = "Most Played: ${mostPlayed ?: "None"}"
        txtRecentGame.text = "Recent Game: ${recent ?: "None"}"
        txtFavorites.text = "$favorites"
        txtHealth.text = "$health%"
        txtRam.text = "Free RAM: ${ram} MB"
        txtStorage.text = "Free Storage: ${storage} GB"
    }

    private fun initViews() {

        txtTotalGames = findViewById(R.id.txtTotalGames)
        txtTotalLaunches = findViewById(R.id.txtTotalLaunches)
        txtMostPlayed = findViewById(R.id.txtMostPlayed)
        txtRecentGame = findViewById(R.id.txtRecentGame)
        txtFavorites = findViewById(R.id.txtFavorites)
        txtHealth = findViewById(R.id.txtHealth)
        txtRam = findViewById(R.id.txtRam)
        txtStorage = findViewById(R.id.txtStorage)
    }
}