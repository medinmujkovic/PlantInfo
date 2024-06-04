package com.example.rma24projekat_19219.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rma24projekat_19219.Biljka
import com.example.rma24projekat_19219.R

class RecyclerViewAdapterMedicinski(private var biljke: List<Biljka>) : RecyclerView.Adapter<RecyclerViewAdapterMedicinski.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicinski_mod, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBiljka = biljke[position]


        holder.nazivItem.text = currentBiljka.naziv
        holder.upozorenjeItem.text = currentBiljka.medicinskoUpozorenje

        val context: Context = holder.slikaItem.context
        var id = context.resources.getIdentifier(currentBiljka.naziv, "drawable", context.packageName)
        holder.slikaItem.setImageResource(id)
        if (id==0) id=context.resources
            .getIdentifier(R.drawable.ic_launcher_background.toString(), "drawable", context.packageName)
        holder.slikaItem.setImageResource(id)


        val koristiList = currentBiljka.medicinskeKoristi
        holder.korist1Item.text = (koristiList.getOrNull(0) ?.opis?: "")
        holder.korist2Item.text = (koristiList.getOrNull(1) ?.opis?: "")
        holder.korist3Item.text = (koristiList.getOrNull(2) ?.opis?: "")

        holder.itemView.setOnClickListener {
            val selectedBiljka = biljke.getOrNull(position)
            if (selectedBiljka == null) {
                updateBiljke(biljke)
                return@setOnClickListener
            }

            val lista = mutableListOf<Biljka>()
            for (biljka in biljke) {
                if (biljka.naziv == selectedBiljka.naziv &&
                    biljka.medicinskoUpozorenje == selectedBiljka.medicinskoUpozorenje &&
                    biljka.medicinskeKoristi[0] == selectedBiljka.medicinskeKoristi[0]  &&
                    biljka.medicinskeKoristi[1]  == selectedBiljka.medicinskeKoristi[1]  &&
                    biljka.medicinskeKoristi[2] == selectedBiljka.medicinskeKoristi[2]
                ) {
                    lista.add(biljka)
                }
            }
            updateBiljke(lista)
        }



    }


    fun updateBiljke(biljke: List<Biljka>) {
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
