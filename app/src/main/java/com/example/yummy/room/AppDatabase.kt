package com.example.yummy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yummy.data.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getFavoriteDao(): FavoriteDao

}