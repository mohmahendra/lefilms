package com.example.lefilms

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert
    fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAllFavorite() : List<Favorite>

    @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE title=:title)")
    fun isFavorite(title: String) : Boolean

    @Delete
    fun deleteFavorite(favorite: Favorite)
}