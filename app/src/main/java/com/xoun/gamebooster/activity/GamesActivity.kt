package com.xoun.gamebooster.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoun.gamebooster.R
import com.xoun.gamebooster.adapters.GameAdapter
import com.xoun.gamebooster.models.GameModel
import com.xoun.gamebooster.utils.GameScanner
import com.xoun.gamebooster.utils.GamePrefs

class GamesActivity : AppCompatActivity() {

    private lateinit var rvGames: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var btnManageGames: TextView

    private lateinit var gameAdapter: GameAdapter

    private var fullList = listOf<GameModel>()
    private var filteredList = mutableListOf<GameModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_games)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvGames = findViewById(R.id.rvGames)
        searchView = findViewById(R.id.searchView)
        btnManageGames = findViewById(R.id.btnManageGames)

        rvGames.layoutManager = LinearLayoutManager(this)

        btnManageGames.setOnClickListener {
            startActivity(Intent(this, ManageGamesActivity::class.java))
        }

        loadGames()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {

                val query = newText.orEmpty()

                val result = fullList.filter {
                    it.appName.contains(query, ignoreCase = true)
                }

                gameAdapter.updateList(result)

                return true
            }
        })
    }

    private fun loadGames() {

        val selectedPackages = GamePrefs.getGames(this)

        fullList = GameScanner.getInstalledGames(this)
            .filter { selectedPackages.contains(it.packageName) }

        filteredList = fullList.toMutableList()

        gameAdapter = GameAdapter(this, filteredList)
        rvGames.adapter = gameAdapter
    }

    override fun onResume() {
        super.onResume()
        loadGames()
    }
}