package com.example.rma24projekat_19219

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterMedicinski(private var biljke: List<Biljka>)
    : RecyclerView.Adapter<RecyclerViewAdapterMedicinski.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medicinski_mod, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nazivItem.text = biljke[position].naziv;
        holder.upozorenjeItem.text = biljke[position].medicinskoUpozorenje;

        val korist1Item = biljke[position].medicinskeKoristi[0]
        holder.korist1Item.text = korist1Item.toString()

        val korist2Item = biljke[position].medicinskeKoristi[1]
        holder.korist2Item.text = korist2Item.toString()

        val korist3Item = biljke[position].medicinskeKoristi[2]
        holder.korist3Item.text = korist3Item.toString()

        val nazivItem: String = biljke[position].naziv
        val context: Context = holder.slikaItem.context

        var id: Int = context.resources
            .getIdentifier(nazivItem, "drawable", context.packageName)
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
        val upozorenjeItem: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist1Item: TextView = itemView.findViewById(R.id.korist1Item)
        val korist2Item: TextView = itemView.findViewById(R.id.korist2Item)
        val korist3Item: TextView = itemView.findViewById(R.id.korist3Item)

    }
}