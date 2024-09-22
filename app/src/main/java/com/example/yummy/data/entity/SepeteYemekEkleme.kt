package com.example.yummy.data.entity

import java.io.Serializable

data class SepeteYemekEkleme(
    var sepet_yemek_id:Int,
    var yemek_adi:String,
    var yemek_resim_adi:String,
    var yemek_fiyat:Int,
    var yemek_siparis_adet:Int,
    var kullanici_adi:String="osman"
): Serializable
