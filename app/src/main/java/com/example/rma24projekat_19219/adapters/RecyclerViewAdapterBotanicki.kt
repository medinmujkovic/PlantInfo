package com.example.rma24projekat_19219.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rma24projekat_19219.data.models.Biljka
import com.example.rma24projekat_19219.data.dao.TrefleDAO
import com.example.rma24projekat_19219.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerViewAdapterBotanicki(private var biljke: List<Biljka>)
    : RecyclerView.Adapter<RecyclerViewAdapterBotanicki.ViewHolder>() {

    private lateinit var trefleDAO: TrefleDAO
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanicki_mod, parent, false)
        trefleDAO = TrefleDAO()
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.slikaItem.context

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val image = trefleDAO.getImage(biljke[position])
                holder.slikaItem.setImageBitmap(image)
            } catch (e: Exception) {
                // Handle exception, e.g., set a placeholder image
                holder.slikaItem.setImageResource(R.drawable.ic_launcher_background)
            }
        }

        holder.nazivItem.text = biljke[position].naziv;
        holder.porodicaItem.text = biljke[position].porodica;
        holder.klimatskiTipItem.text =  biljke[position].klimatskiTipovi.firstOrNull()?.opis?:""
        holder.zemljisniTipItem.text = biljke[position].zemljisniTipovi.firstOrNull()?.naziv?:""

        val porodica: String? = biljke[position].porodica

//        var id: Int = context.resources
//            .getIdentifier(porodica, "drawable", context.packageName)
//        if (id==0) id=context.resources
//            .getIdentifier(R.drawable.ic_launcher_background.toString(), "drawable", context.packageName)
//        holder.slikaItem.setImageResource(id)

        holder.itemView.setOnClickListener{
            val lista= mutableListOf<Biljka>()
            for(i in biljke)
            {
                if(i.porodica==biljke[position].porodica)
                {
                    var klimTip=false
                    var zemljTip=false
                    for(j in i.klimatskiTipovi)
                    {
                        if(j in biljke[position].klimatskiTipovi)
                        {
                            klimTip=true;
                        }
                    }
                    for(j in i.zemljisniTipovi)
                    {
                        if(j in biljke[position].zemljisniTipovi)
                        {
                            zemljTip=true;
                        }
                    }
                    if(klimTip && zemljTip)
                    {
                        lista.add(i)
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
        val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)

    }
}