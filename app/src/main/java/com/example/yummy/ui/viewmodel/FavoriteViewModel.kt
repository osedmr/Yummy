package com.example.yummy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yummy.data.entity.Favorite
import com.example.yummy.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(var frepo: FavoriteRepository) : ViewModel()  {

    val favList= MutableLiveData<List<Favorite>>()


    init {
        favList()
    }
    fun favList(){

         CoroutineScope(Dispatchers.Main).launch {
             favList.value=frepo.favList()
         }
    }

    fun deleteFav(yemek_id: Int){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.deleteFav(yemek_id)
            favList()
        }
    }
}