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
class ProductsViewModel @Inject constructor(var mrepo: MealsRepository): ViewModel() {
    private val _meals = MutableLiveData<List<Meals>>()
    val meals: LiveData<List<Meals>> = _meals

    init {
        mealsYukle()
    }
    fun mealsYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            _meals.value = mrepo.mealsYukle()
        }
    }
}