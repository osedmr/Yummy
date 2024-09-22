package com.example.yummy.data.datasource

import android.util.Log
import com.example.yummy.data.entity.Meals
import com.example.yummy.data.entity.MealsCevap
import com.example.yummy.data.entity.SepeteYemekEkleme
import com.example.yummy.data.entity.SepettekiYemeklerCevap
import com.example.yummy.retrofit.MealsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealsDataSource(var mealsDao: MealsDao) {

    suspend fun mealsYukle(): List<Meals> = withContext(Dispatchers.IO) {
        try {
            val meals = mealsDao.mealsYukle().yemekler
            Log.e("MealsViewModel", "Fetched meals: ${meals.size} items")
            meals
        } catch (e: Exception) {
            Log.e("MealsViewModel", "Error fetching meals", e)
            emptyList()
        }
    }

    suspend fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kisi_adi: String){
        mealsDao.yemeklerEkle( yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet,kisi_adi)
    }
    suspend fun sepettekiYemekleriGetir(kisi_adi: String): List<SepeteYemekEkleme> = withContext(Dispatchers.IO) {
            val gelenveri=mealsDao.yemekleriGetir(kisi_adi).sepet_yemekler
            return@withContext gelenveri
        }


    suspend fun sepettenYemekSil(sepet_yemek_id:Int,kullanici_adi:String) = mealsDao.yemekSil(sepet_yemek_id, kullanici_adi)

}