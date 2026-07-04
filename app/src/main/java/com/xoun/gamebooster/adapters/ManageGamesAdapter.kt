package com.xoun.gamebooster.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xoun.gamebooster.R
import com.xoun.gamebooster.models.GameModel

class ManageGamesAdapter(
    private val apps: MutableList<GameModel>
) : RecyclerView.Adapter<ManageGamesAdapter.GameViewHolder>() {

    class GameViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val imgGame: ImageView =
            view.findViewById(R.id.imgGame)

        val txtGameName: TextView =
            view.findViewById(R.id.txtGameName)

        val txtPackage: TextView =
            view.findViewById(R.id.txtPackage)

        val checkGame: CheckBox =
            view.findViewById(R.id.checkGame)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_manage_game,
                    parent,
                    false
                )

        return GameViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GameViewHolder,
        position: Int
    ) {

        val app = apps[position]

        holder.imgGame.setImageDrawable(app.icon)

        holder.txtGameName.text =
            app.appName

        holder.txtPackage.text =
            app.packageName

        holder.checkGame.setOnCheckedChangeListener(null)

        holder.checkGame.isChecked =
            app.isSelected

        holder.checkGame.setOnCheckedChangeListener { _, isChecked ->

            app.isSelected =
                isChecked
        }

        holder.itemView.setOnClickListener {

            app.isSelected =
                !app.isSelected

            holder.checkGame.isChecked =
                app.isSelected
        }
    }

    override fun getItemCount(): Int {
        return apps.size
    }
}