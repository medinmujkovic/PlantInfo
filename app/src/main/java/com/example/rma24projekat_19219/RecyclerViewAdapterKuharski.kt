package com.example.rma24projekat_19219

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapterKuharski(private var biljke: List<Biljka>)
    : RecyclerView.Adapter<RecyclerViewAdapterKuharski.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.kuharski_mod, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nazivItem.text = biljke[position].naziv;
        holder.profilOkusaItem.text = biljke[position].profilOkusa.opis

        val jelaList = biljke[position].jela

        try {
            val jelo1Item = jelaList[0]
            holder.jelo1Item.text = jelo1Item
        } catch (e: IndexOutOfBoundsException) {
            holder.jelo1Item.text = ""
        }

        try {
            val jelo2Item = jelaList.getOrNull(1)
            holder.jelo2Item.text = jelo2Item ?: ""
        } catch (e: IndexOutOfBoundsException) {
            holder.jelo2Item.text = ""
        }

        try {
            val jelo3Item = jelaList.getOrNull(2)
            holder.jelo3Item.text = jelo3Item ?: ""
        } catch (e: IndexOutOfBoundsException) {
            holder.jelo3Item.text = ""
        }

        val profilOkusaItem: String = biljke[position].profilOkusa.toString()
        val context: Context = holder.slikaItem.context

        var id: Int = context.resources
            .getIdentifier(profilOkusaItem, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier(R.drawable.ic_launcher_background.toString(), "drawable", context.packageName)
        holder.slikaItem.setImageResource(id)

        holder.itemView.setOnClickListener {
            val lista = mutableListOf<Biljka>()
            val selectedBiljka = biljke[position]

            if (selectedBiljka == null) {
                updateBiljke(biljke)
                return@setOnClickListener
            }

            for (biljka in biljke) {
                if (biljka.profilOkusa == selectedBiljka.profilOkusa) {
                    val klimatskiMatches = biljka.klimatskiTipovi.containsAll(selectedBiljka.klimatskiTipovi)
                    val zemljisniMatches = biljka.zemljisniTipovi.containsAll(selectedBiljka.zemljisniTipovi)

                    if (klimatskiMatches && zemljisniMatches) {
                        lista.add(biljka)
                    }
                }
            }

            updateBiljke(lista)
        }

    }
    fun updateBiljke (biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        val profilOkusaItem: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1Item: TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2Item: TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3Item: TextView = itemView.findViewById(R.id.jelo3Item)
    }
}