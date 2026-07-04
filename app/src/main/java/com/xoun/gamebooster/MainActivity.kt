package com.xoun.gamebooster

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xoun.gamebooster.activity.BoostActivity
import com.xoun.gamebooster.activity.DashboardActivity
import com.xoun.gamebooster.activity.GamesActivity
import com.xoun.gamebooster.utils.RamUtils
import com.xoun.gamebooster.utils.StorageUtils

class MainActivity : AppCompatActivity() {
    private lateinit var txtRam: TextView
    private lateinit var txtBattery: TextView
    private lateinit var btnBoost: Button
    private lateinit var btnGames: Button
    private lateinit var txtStorageTotal: TextView
    private lateinit var txtStorageUsed: TextView
    private lateinit var txtStorageFree: TextView
    private lateinit var btnDashboard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d(
            "RAM_UTILS",
            "Total RAM = ${RamUtils.getTotalRam(this)} MB"
        )

        Log.d(
            "RAM_UTILS",
            "Used RAM = ${RamUtils.getUsedRam(this)} MB"
        )

        Log.d(
            "RAM_UTILS",
            "Available RAM = ${RamUtils.getAvailableRam(this)} MB"
        )

        Log.d(
            "STORAGE_UTILS",
            "Total Storage = ${StorageUtils.getTotalStorageGB()} GB"
        )

        Log.d(
            "STORAGE_UTILS",
            "Used Storage = ${StorageUtils.getUsedStorageGB()} GB"
        )

        Log.d(
            "STORAGE_UTILS",
            "Free Storage = ${StorageUtils.getFreeStorageGB()} GB"
        )

        initViews()

        displayRamInfo()
        displayBatteryInfo()
        displayStorageInfo()

        btnBoost.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    BoostActivity::class.java
                )
            )
        }

        btnGames.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    GamesActivity::class.java
                )
            )
        }
        btnDashboard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )
        }
    }

    private fun initViews() {
        txtRam = findViewById(R.id.txtRam)
        txtBattery = findViewById(R.id.txtBattery)
        btnBoost = findViewById(R.id.btnBoost)
        btnGames = findViewById(R.id.btnGames)
        txtStorageTotal = findViewById(R.id.txtStorageTotal)
        txtStorageUsed = findViewById(R.id.txtStorageUsed)
        txtStorageFree = findViewById(R.id.txtStorageFree)
        btnDashboard = findViewById(R.id.btnDashboard)
    }

    private fun displayRamInfo() {

        val activityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()

        activityManager.getMemoryInfo(memoryInfo)

        val availableRam =
            memoryInfo.availMem / (1024 * 1024)

        txtRam.text =
            "RAM Available: ${availableRam} MB"
    }

    private fun displayBatteryInfo() {

        val batteryStatus = registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val level =
            batteryStatus?.getIntExtra(
                BatteryManager.EXTRA_LEVEL,
                -1
            ) ?: -1

        txtBattery.text =
            "Battery: $level%"
    }

    private fun displayStorageInfo() {

        txtStorageTotal.text =
            "Total Storage : ${StorageUtils.getTotalStorageGB()} GB"

        txtStorageUsed.text =
            "Used Storage : ${StorageUtils.getUsedStorageGB()} GB"

        txtStorageFree.text =
            "Free Storage : ${StorageUtils.getFreeStorageGB()} GB"
    }
}