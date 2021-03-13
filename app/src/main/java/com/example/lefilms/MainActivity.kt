package com.example.lefilms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OptionsBottomSheetFragment.ItemClickListener {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesLayoutManager: LinearLayoutManager
    private var filterType: Int = 0
    private var popularMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovies = findViewById(R.id.recycler_view)
        popularMoviesLayoutManager = LinearLayoutManager(this)
        popularMovies.layoutManager = popularMoviesLayoutManager
        popularMoviesAdapter = MovieAdapter(mutableListOf()) {movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        getListMovies()

        bt_movie.setOnClickListener {
            supportFragmentManager.let {
                OptionsBottomSheetFragment.newInstance(Bundle()).apply {
                    show(it, tag)
                }
            }
        }

    }

    private fun getListMovies() {
        when (filterType) {
            0 -> MoviesRepository.getPopularMovies(
                1,
                onSuccess = { movies ->
                    popularMoviesAdapter.updateMovies(movies)
                    attachPopularMoviesOnScrollListener()
                },
                onError = {
                    Toast.makeText(
                        this, getString(R.string.app_name), Toast.LENGTH_SHORT
                    ).show()
                }
            )
            1 -> MoviesRepository.getTopRatedMovies(
                1,
                onSuccess = { movies ->
                    popularMoviesAdapter.updateMovies(movies)
                    attachPopularMoviesOnScrollListener()
                },
                onError = {
                    Toast.makeText(
                        this, getString(R.string.app_name), Toast.LENGTH_SHORT
                    ).show()
                }
            )
            else -> {
                MoviesRepository.getNowPlayingMovies(
                    1,
                    onSuccess = { movies ->
                        popularMoviesAdapter.updateMovies(movies)
                        attachPopularMoviesOnScrollListener()
                    },
                    onError = {
                        Toast.makeText(
                            this, getString(R.string.app_name), Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    private fun appendListMovies() {
        when (filterType) {
            0 -> MoviesRepository.getPopularMovies(
                popularMoviesPage,
                onSuccess = { movies ->
                    popularMoviesAdapter.appendMovies(movies)
                    attachPopularMoviesOnScrollListener()
                },
                onError = {
                    Toast.makeText(
                        this, getString(R.string.app_name), Toast.LENGTH_SHORT
                    ).show()
                }
            )
            1 -> MoviesRepository.getTopRatedMovies(
                popularMoviesPage,
                onSuccess = { movies ->
                    popularMoviesAdapter.appendMovies(movies)
                    attachPopularMoviesOnScrollListener()
                },
                onError = {
                    Toast.makeText(
                        this, getString(R.string.app_name), Toast.LENGTH_SHORT
                    ).show()
                }
            )
            else -> {
                MoviesRepository.getNowPlayingMovies(
                    popularMoviesPage,
                    onSuccess = { movies ->
                        popularMoviesAdapter.appendMovies(movies)
                        attachPopularMoviesOnScrollListener()
                    },
                    onError = {
                        Toast.makeText(
                            this, getString(R.string.app_name), Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutManager.itemCount
                val visibleItemCount = popularMoviesLayoutManager.childCount
                val firstVisibleItem = popularMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    appendListMovies()
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_POSTER, movie.poster)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.description)
        intent.putExtra("object", movie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.favoritePage) {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: String) {
        when (item) {
            "Popular" -> {
                filterType = 0
                getListMovies()
            }

            "TopRated" -> {
                filterType = 1
                getListMovies()
            }

            else -> {
                filterType = 2
                getListMovies()
            }
        }
    }
}
