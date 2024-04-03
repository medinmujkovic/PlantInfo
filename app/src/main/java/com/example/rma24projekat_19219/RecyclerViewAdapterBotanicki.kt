package com.example.rma24projekat_19219

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterBotanicki(private var biljke: List<Biljka>)
    : RecyclerView.Adapter<RecyclerViewAdapterBotanicki.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanicki_mod, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nazivItem.text = biljke[position].naziv;
        holder.porodicaItem.text = biljke[position].porodica;

        val klimatskiTipovi = biljke[position].klimatskiTipovi.joinToString(", ")
        holder.klimatskiTipItem.text = klimatskiTipovi

        val zemljisniTipovi = biljke[position].zemljisniTipovi.joinToString(", ")
        holder.zemljisniTipItem.text = zemljisniTipovi

        val porodica: String = biljke[position].porodica
        val context: Context = holder.slikaItem.context

        var id: Int = context.resources
            .getIdentifier(porodica, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier(R.drawable.ic_launcher_background.toString(), "drawable", context.packageName)
        holder.slikaItem.setImageResource(id)
    }
    fun updateBiljke (biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)

    }
}