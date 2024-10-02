package com.example.yummy.ui.adapters

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.yummy.data.entity.Meals
import com.example.yummy.databinding.MealsCartViewBinding
import com.example.yummy.ui.fragment.ProductsFragmentDirections

import com.example.yummy.ui.viewmodel.ProductsViewModel


class MealsAdapter(
    private val viewModel: ProductsViewModel
) : ListAdapter<Meals, MealsAdapter.MealsViewHolder>(MealsDiffCallback()) {

    inner class MealsViewHolder(var binding: MealsCartViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val binding = MealsCartViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val meals = getItem(position) // `getItem` ile öğeyi al
        val b = holder.binding
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${meals.yemek_resim_adi}"
        Glide.with(b.root.context).load(url).override(300, 300).into(b.mealImage)
        b.mealName.text = meals.yemek_adi
        b.mealPrice.text = "${meals.yemek_fiyat} ₺"

        b.mealsCardView.setOnClickListener {
            val gecis = ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(meals = meals)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

    // DiffUtil sınıfı
    class MealsDiffCallback : DiffUtil.ItemCallback<Meals>() {
        override fun areItemsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            return oldItem.yemek_id == newItem.yemek_id // Benzersiz kimliği karşılaştır
        }

        override fun areContentsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            return oldItem == newItem // İçeriklerin eşitliğini kontrol et
        }
    }
}
