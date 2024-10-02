package com.example.yummy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.data.entity.Meals
import com.example.yummy.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductsViewModel @Inject constructor(private val mrepo: MealsRepository) : ViewModel() {

    private val _meals = MutableLiveData<List<Meals>>()
    val meals: LiveData<List<Meals>> = _meals

    private lateinit var originalMealList: List<Meals> // Orijinal yemek listesi

    init {
        mealsYukle()
    }

    fun mealsYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            originalMealList = mrepo.mealsYukle() // Orijinal listeyi yükle
            _meals.value = originalMealList // Başlangıçta tüm yemekleri göster
        }
    }

    fun sortByName() {
        _meals.value = _meals.value?.sortedBy { it.yemek_adi }?.toMutableList()
    }

    fun sortByPrice() {
        _meals.value = _meals.value?.sortedBy { it.yemek_fiyat }?.toMutableList()
    }

    fun filterByPriceRange(minPrice: Int) {
        _meals.value = originalMealList.filter { it.yemek_fiyat >= minPrice } // Filtreleme işlemi
    }


    fun resetFilters() {
        _meals.value = originalMealList // Filtreleri sıfırla ve orijinal listeyi geri yükle
    }
}
