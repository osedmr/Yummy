package com.example.yummy.data.datasource

import com.example.yummy.data.entity.Favorite
import com.example.yummy.room.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(var favDao: FavoriteDao)  {

    suspend fun favList():List<Favorite> =
        withContext(Dispatchers.IO){
            return@withContext favDao.allFavorite()

        }

    suspend fun favoriEkle(yemek_adi: String, yemek_resim: String, yemek_fiyat: Int) = withContext(Dispatchers.IO) {
        val mevcutFavori = favDao.getFavoriYemekByName(yemek_adi)
        if (mevcutFavori == null) {
            val yeniFavori = Favorite(yemek_adi = yemek_adi, yemek_resim = yemek_resim, yemek_fiyat = yemek_fiyat)
            favDao.addFavorite(yeniFavori)
        }
    }

    suspend fun deleteFav(yemek_id: Int)=
        withContext(Dispatchers.IO) {
            val deleteFav =
                Favorite(yemek_id = yemek_id, yemek_adi = "", yemek_resim = "", yemek_fiyat = 0)
            favDao.delete(deleteFav)
        }

}