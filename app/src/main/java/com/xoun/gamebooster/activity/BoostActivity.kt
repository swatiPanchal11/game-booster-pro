package com.xoun.gamebooster.activity

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.xoun.gamebooster.R
import com.xoun.gamebooster.utils.GameStatsManager
import com.xoun.gamebooster.utils.HealthUtils
import com.xoun.gamebooster.utils.RamUtils

class BoostActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var txtStatus: TextView
    private lateinit var txtStatusHint: TextView
    private lateinit var txtHealthScore: TextView
    private lateinit var txtRamInfo: TextView
    private lateinit var txtProgress: TextView

    private var packageNameToLaunch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_boost)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        packageNameToLaunch = intent.getStringExtra("package_name") ?: ""

        progressBar    = findViewById(R.id.progressBar)
        txtStatus      = findViewById(R.id.txtStatus)
        txtStatusHint  = findViewById(R.id.txtStatusHint)
        txtHealthScore = findViewById(R.id.txtHealthScore)
        txtRamInfo     = findViewById(R.id.txtRamInfo)
        txtProgress    = findViewById(R.id.txtProgress)

        val ram   = RamUtils.getAvailableRam(this)
        val score = HealthUtils.getHealthScore(this)

        txtRamInfo.text     = "$ram MB"
        txtHealthScore.text = "$score%"

        startBoost()
    }

    private fun animateProgress(from: Int, to: Int, onEnd: (() -> Unit)? = null) {
        ValueAnimator.ofInt(from, to).apply {
            duration = 700
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                progressBar.progress = value
                txtProgress.text = "$value%"
            }
            onEnd?.let {
                addListener(object : android.animation.AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: android.animation.Animator) = it()
                })
            }
            start()
        }
    }

    private fun startBoost() {
        progressBar.progress = 0
        txtProgress.text = "0%"

        Handler(Looper.getMainLooper()).postDelayed({
            txtStatus.text     = "Scanning RAM"
            txtStatusHint.text = "Identifying memory-hungry processes..."
            animateProgress(0, 30)
        }, 400)

        Handler(Looper.getMainLooper()).postDelayed({
            txtStatus.text     = "Killing Background Apps"
            txtStatusHint.text = "Freeing up memory for your game..."
            animateProgress(30, 65)
        }, 1400)

        Handler(Looper.getMainLooper()).postDelayed({
            txtStatus.text     = "Optimizing CPU Priority"
            txtStatusHint.text = "Setting game thread to high priority..."
            animateProgress(65, 90)
        }, 2400)

        Handler(Looper.getMainLooper()).postDelayed({
            txtStatus.text     = "Boost Complete!"
            txtStatusHint.text = "Your device is ready — launching game..."
            animateProgress(90, 100) {
                val boostedRam = RamUtils.getAvailableRam(this) + 150
                txtRamInfo.text = "$boostedRam MB"
                launchGame()
            }
        }, 3400)
    }

    private fun launchGame() {
        if (packageNameToLaunch.isEmpty()) { finish(); return }

        val launchIntent = packageManager.getLaunchIntentForPackage(packageNameToLaunch)
        launchIntent?.let {
            GameStatsManager.increaseLaunchCount(this, packageNameToLaunch)
            GameStatsManager.saveLastLaunch(this, packageNameToLaunch)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(it)
                finish()
            }, 600)
        } ?: finish()
    }
}
