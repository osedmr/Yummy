package com.example.yummy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "yemek_id") var yemek_id: Int = 0,
    @ColumnInfo(name = "yemek_adi") var yemek_adi: String,
    @ColumnInfo(name = "yemek_resim") var yemek_resim: String,
    @ColumnInfo(name = "yemek_fiyat") var yemek_fiyat: Int
)
