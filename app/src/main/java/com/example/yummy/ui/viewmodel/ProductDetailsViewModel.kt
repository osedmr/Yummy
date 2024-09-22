package com.example.yummy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(var mrepo: MealsRepository) : ViewModel() {

    val quantity = MutableLiveData<Int>().apply { value = 1 }  // Varsayılan adet: 1
    val totalPrice = MutableLiveData<Int>()
    private var mealPrice: Int = 0  // Yemek fiyatı başlangıçta sıfır

    init {
        calculateTotalPrice()
    }

    fun setMealPrice(price: Int) {
        mealPrice = price
        calculateTotalPrice()
    }

    fun incrementQuantity() {
        val currentQuantity = quantity.value ?: 1
        quantity.value = currentQuantity + 1
        calculateTotalPrice()
    }

    fun decrementQuantity() {
        val currentQuantity = quantity.value ?: 1
        if (currentQuantity > 1) {
            quantity.value = currentQuantity - 1
            calculateTotalPrice()
        }
    }

    private fun calculateTotalPrice() {
        val currentQuantity = quantity.value ?: 1
        totalPrice.value = currentQuantity * mealPrice
    }

    fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kisi_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mrepo.sepeteYemekEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet,kisi_adi)
        }

    }
}