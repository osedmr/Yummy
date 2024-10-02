package com.example.yummy.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yummy.R
import com.example.yummy.databinding.FragmentPriceFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PriceFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPriceFilterBottomSheetBinding
    var onPriceSelected: ((Int) -> Unit)? = null // Fiyat seçildiğinde çağrılacak fonksiyon
    var onResetFilters: (() -> Unit)? = null // Filtreleri sıfırlamak için callback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceFilterBottomSheetBinding.inflate(inflater, container, false)

        // Slider ayarları
        binding.priceSlider.apply {
            valueFrom = 0f // Başlangıç değeri 0
            valueTo = 390f // Maksimum değer 250
            stepSize = 30f // Adım boyutu 30
            setLabelFormatter { value -> "${value.toInt()} ₺" } // Slider değerinin etiket formatı
        }

        // Filtreyi uygula butonuna tıkladığında
        binding.filterButton.setOnClickListener {
            val selectedMinPrice = binding.priceSlider.value.toInt()
            onPriceSelected?.invoke(selectedMinPrice)  // Seçilen fiyatı ViewModel'e gönder
            dismiss()  // Dialog'u kapat
        }

        // Sıfırlama butonuna tıklandığında
        binding.resetButton.setOnClickListener {
            onResetFilters?.invoke() // Filtreleri sıfırla
            dismiss()  // Dialog'u kapat
        }

        return binding.root // Görünüm döndür
    }
}




