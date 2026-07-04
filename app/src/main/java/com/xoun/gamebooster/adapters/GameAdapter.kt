package com.xoun.gamebooster.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xoun.gamebooster.R
import com.xoun.gamebooster.activity.GamesDetailsActivity
import com.xoun.gamebooster.models.GameModel
import com.xoun.gamebooster.utils.FavoriteManager

class GameAdapter(
    private val context: Context,
    private var games: List<GameModel>
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgGame: ImageView = view.findViewById(R.id.imgGame)
        val txtGameName: TextView = view.findViewById(R.id.txtGameName)
        val txtPackage: TextView = view.findViewById(R.id.txtPackage)
        val imgFavorite: ImageView = view.findViewById(R.id.imgFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)

        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {

        val game = games[position]

        holder.txtGameName.text = game.appName
        holder.txtPackage.text = game.packageName
        holder.imgGame.setImageDrawable(game.icon)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, GamesDetailsActivity::class.java)

            intent.putExtra("app_name", game.appName)
            intent.putExtra("package_name", game.packageName)

            context.startActivity(intent)
        }

        val isFav = FavoriteManager.isFavorite(
            context,
            game.packageName
        )

        holder.imgFavorite.setImageResource(
            if (isFav)
                android.R.drawable.btn_star_big_on
            else
                android.R.drawable.btn_star_big_off
        )

        holder.imgFavorite.setOnClickListener {

            val current = FavoriteManager.isFavorite(
                context,
                game.packageName
            )

            FavoriteManager.setFavorite(
                context,
                game.packageName,
                !current
            )

            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateList(newList: List<GameModel>) {
        games = newList
        notifyDataSetChanged()
    }
}