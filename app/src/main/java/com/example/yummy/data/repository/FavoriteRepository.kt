package com.example.yummy.data.repository

import com.example.yummy.data.datasource.FavoriteDataSource
import com.example.yummy.data.entity.Favorite

class FavoriteRepository(var favDs: FavoriteDataSource) {

    suspend fun favList():List<Favorite> =favDs.favList()
    suspend fun addFavorite(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int){
        favDs.favoriEkle(yemek_adi, yemek_resim_adi, yemek_fiyat)
    }
    suspend fun deleteFav(yemek_id: Int)=favDs.deleteFav(yemek_id)
}