package com.xoun.gamebooster.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoun.gamebooster.R
import com.xoun.gamebooster.adapters.ManageGamesAdapter
import com.xoun.gamebooster.utils.GamePrefs
import com.xoun.gamebooster.utils.GameScanner

class ManageGamesActivity : AppCompatActivity() {
    private lateinit var rvApps: RecyclerView
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manage_games)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvApps = findViewById(R.id.rvApps)
        btnSave = findViewById(R.id.btnSave)

        val apps =
            GameScanner.getInstalledGames(this)
                .toMutableList()

        val savedGames =
            GamePrefs.getGames(this)

        apps.forEach {

            it.isSelected =
                savedGames.contains(
                    it.packageName
                )
        }

        val adapter =
            ManageGamesAdapter(
                apps
            )

        rvApps.layoutManager =
            LinearLayoutManager(this)

        rvApps.adapter =
            adapter

        btnSave.setOnClickListener {

            val selectedPackages =
                apps.filter {
                    it.isSelected
                }
                    .map {
                        it.packageName
                    }
                    .toSet()

            GamePrefs.saveGames(
                this,
                selectedPackages
            )

            finish()
        }
    }
}