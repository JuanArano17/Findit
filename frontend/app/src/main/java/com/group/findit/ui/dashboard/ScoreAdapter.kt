package com.group.findit.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group.findit.R
import java.text.NumberFormat
import java.util.Locale

class ScoreAdapter(
    private val nombres: List<String>,
    private val puntajes: List<Int>
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreJugador: TextView = itemView.findViewById(R.id.textNombre)
        val puntajeJugador: TextView = itemView.findViewById(R.id.textPuntaje)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.nombreJugador.text = nombres[position]
        // como no va a haber numeros decimales entonces no se requiere internacionalizarlo
        holder.puntajeJugador.text = puntajes[position].toString()
    }

    override fun getItemCount(): Int = nombres.size
}
