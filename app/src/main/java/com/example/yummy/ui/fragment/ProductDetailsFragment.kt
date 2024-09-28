package com.example.yummy.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.yummy.R
import com.example.yummy.databinding.FragmentProductDetailsBinding
import com.example.yummy.ui.viewmodel.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProductDetailsViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentProductDetailsBinding.inflate(inflater,container,false)


        binding.close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val bundle: ProductDetailsFragmentArgs by navArgs()
        val meals=bundle.meals
        binding.mealsName.text=meals.yemek_adi
        binding.price.text="${meals.yemek_fiyat} ₺"

        viewModel.setMealPrice(meals.yemek_fiyat.toInt())

        // Yemek resmini yükle
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${meals.yemek_resim_adi}"
        Glide.with(this).load(url).override(300, 300).into(binding.mealsImage)

        // Adet ve toplam fiyat güncellemelerini gözlemle
        viewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            binding.pieceTotal.text = quantity.toString()
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            binding.totalPrice.text = "$price ₺"
        }

        // Plus ve Minus butonlarını ayarla
        binding.plus.setOnClickListener {
            viewModel.incrementQuantity()
        }

        binding.minus.setOnClickListener {
            viewModel.decrementQuantity()
        }

        binding.addToCart.setOnClickListener {
            val yemek_adi=binding.mealsName.text.toString()
            val yemek_resim_adi=bundle.meals.yemek_resim_adi
            val yemek_fiyat = binding.totalPrice.text.toString()
                .replace("₺", "")
                .trim()
                .toInt()  // Hata burada düzeltiliyor
            val yemek_siparis_adet=binding.pieceTotal.text.toString().toInt()
            val kisi_adi="Osman"
            sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kisi_adi)
        }


        binding.addFavorite.setOnClickListener {
            binding.address.playAnimation()
            viewModel.addFavorite(meals.yemek_adi,meals.yemek_resim_adi,meals.yemek_fiyat)
            Toast.makeText(requireContext(),"${meals.yemek_adi} favoriye eklendi",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


    fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kisi_adi:String){
        if(yemek_adi.isNotEmpty() && yemek_resim_adi.isNotEmpty() && yemek_fiyat>0 && yemek_siparis_adet>0 && kisi_adi.isNotEmpty()){
            viewModel.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kisi_adi)
        }
    }
}