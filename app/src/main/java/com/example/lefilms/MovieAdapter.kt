package com.example.lefilms

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_item.view.*

class MovieAdapter(
    private var listMovie: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.tv_title.text = listMovie.get(position).title
        holder.itemView.tv_date.text = listMovie.get(position).releaseDate
        holder.itemView.tv_description.text = listMovie.get(position).description
        Picasso.get().load("https://image.tmdb.org/t/p/w342${listMovie.get(position).poster}")
            .into(holder.itemView.iv_poster)
        holder.itemView.setOnClickListener {
            onMovieClick.invoke(listMovie.get(position))
            Log.d("abcd", listMovie.get(position).toString())
        }
    }

    fun updateMovies(movies: List<Movie>) {
        this.listMovie.clear()
        this.listMovie.addAll(movies)
        notifyDataSetChanged()
    }

    fun appendMovies(movies: List<Movie>) {
        this.listMovie.addAll(movies)
        notifyItemRangeInserted(
            listMovie.size,
            movies.size - 1
        )
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view)

}