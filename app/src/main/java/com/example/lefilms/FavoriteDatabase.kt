package com.example.lefilms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object {

        @Volatile
        private var INSTANCE : FavoriteDatabase? = null

        fun buildDatabase(context: Context) : FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "myfavdb"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}