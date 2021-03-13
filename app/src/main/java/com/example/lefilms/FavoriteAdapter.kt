package com.example.lefilms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_item.view.*

class FavoriteAdapter(
    private var listFavorite : MutableList<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.tv_title.text = listFavorite.get(position).title
        holder.itemView.tv_date.text = listFavorite.get(position).date
        holder.itemView.tv_description.text = listFavorite.get(position).overview
        Picasso.get().load("https://image.tmdb.org/t/p/w342${listFavorite.get(position).posterPath}")
            .into(holder.itemView.iv_poster)
    }

    fun loadFavorite(favorite: List<Favorite>) {
        this.listFavorite.addAll(favorite)
        notifyDataSetChanged()
    }

    class Holder(view : View) : RecyclerView.ViewHolder(view)
}