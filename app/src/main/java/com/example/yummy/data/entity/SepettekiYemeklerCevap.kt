package com.example.yummy.data.entity

import java.io.Serializable

data class SepettekiYemeklerCevap(
    var sepet_yemekler: List<SepeteYemekEkleme>,
    var success: Int
): Serializable
