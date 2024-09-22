package com.example.yummy.data.repository

import com.example.yummy.data.datasource.MealsDataSource
import com.example.yummy.data.entity.Meals
import com.example.yummy.data.entity.SepeteYemekEkleme
import javax.inject.Inject

class MealsRepository @Inject constructor(var mds: MealsDataSource)  {

    suspend fun mealsYukle() : List<Meals> =mds.mealsYukle()
    suspend fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kisi_adi: String) =mds.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet,kisi_adi)

    suspend fun sepettekiYemekleriGetir(kisi_adi: String): List<SepeteYemekEkleme> = mds.sepettekiYemekleriGetir(kisi_adi)

    suspend fun sepettenYemekSil(sepet_yemek_id:Int,kullanici_adi:String) = mds.sepettenYemekSil(sepet_yemek_id, kullanici_adi)
}