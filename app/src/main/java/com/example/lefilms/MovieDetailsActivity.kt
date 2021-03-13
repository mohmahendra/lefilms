package com.example.lefilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.movies_item.view.*
import java.util.*
import kotlin.math.log

const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        poster = findViewById(R.id.iv_posterDetails)
        title = findViewById(R.id.tv_titleDetails)
        releaseDate = findViewById(R.id.tv_dateDetails)
        overview = findViewById(R.id.tv_descriptionDetails)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        val db = FavoriteDatabase.buildDatabase(this)

        val obj : Movie? = intent.getParcelableExtra("object")

        if (obj != null) {
            if (db.favoriteDao().isFavorite(obj.title)) {
                iv_favorite.setImageResource(R.drawable.ic_favorite)
            } else {
                iv_favorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        iv_favorite.setOnClickListener {
            if (obj != null) {
                if (db.favoriteDao().isFavorite(obj.title)) {
                    db.favoriteDao().deleteFavorite(
                        Favorite(
                            id = obj.id,
                            title = obj.title,
                            date = obj.releaseDate,
                            overview = obj.description,
                            posterPath = obj.poster
                        )
                    )
                    iv_favorite.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    db.favoriteDao().addFavorite(
                        Favorite(
                            id = obj.id,
                            title = obj.title,
                            date = obj.releaseDate,
                            overview = obj.description,
                            posterPath = obj.poster
                        )
                    )
                    iv_favorite.setImageResource(R.drawable.ic_favorite)
                }
            }
        }

    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Picasso.get().load("https://image.tmdb.org/t/p/w342${posterPath}").into(poster)
        }
        title.text = extras.getString(MOVIE_TITLE, "")
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

}
