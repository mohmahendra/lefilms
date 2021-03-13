package com.example.lefilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favoriteMovies = findViewById(R.id.rv_favorite)
        favoriteMovies.layoutManager = LinearLayoutManager(this)
        favoriteMoviesAdapter = FavoriteAdapter(mutableListOf())
        favoriteMovies.adapter = favoriteMoviesAdapter

        val db = FavoriteDatabase.buildDatabase(this)

        favoriteMoviesAdapter.loadFavorite(db.favoriteDao().getAllFavorite())
    }
}
