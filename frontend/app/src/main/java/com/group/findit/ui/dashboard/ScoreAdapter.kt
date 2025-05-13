package com.group.findit.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group.findit.R

/**
 * Adapter for displaying the scoreboard in a RecyclerView.
 * @param nombres List of player names.
 * @param puntajes List of player scores.
 */
class ScoreAdapter(
    private val nombres: List<String>,
    private val puntajes: List<Int>
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    /**
     * ViewHolder for a single scoreboard item.
     * Holds references to the views for displaying player name and score.
     */
    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreJugador: TextView = itemView.findViewById(R.id.textNombre)
        val puntajeJugador: TextView = itemView.findViewById(R.id.textPuntaje)
    }

    /**
     * Inflates the layout for a single scoreboard item.
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new View.
     * @return A new instance of [ScoreViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    /**
     * Binds the data to the ViewHolder.
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the data set.
     */
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.nombreJugador.text = nombres[position]
        holder.puntajeJugador.text = puntajes[position].toString()
    }

    /**
     * Returns the total number of items in the data set.
     * @return The size of the data set.
     */
    override fun getItemCount(): Int = nombres.size
}
