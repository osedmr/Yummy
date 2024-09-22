package com.example.yummy.retrofit

import com.example.yummy.data.entity.MealsCevap
import com.example.yummy.data.entity.SepeteYemekEklemeCevap
import com.example.yummy.data.entity.SepettekiYemeklerCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MealsDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun mealsYukle() : MealsCevap

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun yemeklerEkle(
        @Field("yemek_adi") yemek_adi:String,
        @Field("yemek_resim_adi") yemek_resim_adi:String,
        @Field("yemek_fiyat") yemek_fiyat:Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
        @Field("kullanici_adi") kullanici_adi:String="osman"): SepeteYemekEklemeCevap


    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun yemekleriGetir(
        @Field("kullanici_adi") kullanici_adi:String="osman"): SepettekiYemeklerCevap

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun yemekSil(
        @Field("sepet_yemek_id") sepet_yemek_id:Int,
        @Field("kullanici_adi") kullanici_adi:String="osman"): SepeteYemekEklemeCevap

}