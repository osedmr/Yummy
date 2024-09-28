package com.example.yummy.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.yummy.data.entity.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite WHERE yemek_adi = :yemek_adi LIMIT 1")
    suspend fun getFavoriYemekByName(yemek_adi: String): Favorite?

    @Query("SELECT * FROM favorite")
    suspend fun allFavorite(): List<Favorite>

    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}