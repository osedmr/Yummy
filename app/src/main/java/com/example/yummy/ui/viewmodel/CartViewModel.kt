package com.example.yummy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.data.entity.SepeteYemekEkleme
import com.example.yummy.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject constructor(var mrepo: MealsRepository) : ViewModel() {

    private val _sepetYemekler = MutableLiveData<List<SepeteYemekEkleme>>()
    val sepetYemekler: LiveData<List<SepeteYemekEkleme>> get() = _sepetYemekler

    private val _toplamFiyat = MutableLiveData<Double>()
    val toplamFiyat: LiveData<Double> get() = _toplamFiyat


    init {
        sepettekiYemekleriGetir("osman")
    }
    fun sepettekiYemekleriGetir(kisi_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val yemekler = mrepo.sepettekiYemekleriGetir(kisi_adi)
                _sepetYemekler.value = yemekler
                toplamFiyatiHesapla()
                Log.d("CartViewModel", "Yemekler getirildi: $yemekler")
            } catch (e: Exception) {
                Log.e("CartViewModel", "Hata: ${e.message}")
            }
        }
    }
    fun sepettenYemekSil( sepet_yemek_id: Int,kisi_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mrepo.sepettenYemekSil(sepet_yemek_id,kisi_adi)
        }
        sepettekiYemekleriGetir("osman")
    }

    fun toplamFiyatiHesapla() {
        val toplam= _sepetYemekler.value?.sumByDouble { it.yemek_fiyat.toDouble() } ?: 0.0
        _toplamFiyat.value = toplam
        Log.d("CartViewModels", "Toplam fiyat hesaplandı: $toplam")
    }

}
