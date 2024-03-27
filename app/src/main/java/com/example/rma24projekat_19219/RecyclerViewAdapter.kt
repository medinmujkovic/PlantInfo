package com.example.rma24projekat_19219

import android.content.Context
import android.graphics.drawable.AnimatedImageDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private var biljke: List<AnimatedImageDrawable>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = biljke.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = biljke[position].title;
        val genreMatch: String = biljke[position].genre
        //Pronalazimo id drawable elementa na osnovu naziva zanra
        val context: Context = holder.image.context
        var id: Int = context.resources
            .getIdentifier(genreMatch, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        holder.image.setImageResource(id)
    }
    fun updateMovies(biljke: List<AnimatedImageDrawable>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
    }
}