package com.example.yummy.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummy.databinding.FragmentCartBinding
import com.example.yummy.ui.adapters.CartAdapter
import com.example.yummy.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.cartRV.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(requireContext(), mutableListOf(), viewModel)
        binding.cartRV.adapter = adapter
        binding.blankAnimation.playAnimation() // Animasyonu başlat
        binding.blankAnimation.loop(true)
        binding.close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        observeData("osman")


        viewModel.toplamFiyat.observe(viewLifecycleOwner) { total ->
            binding.totalprice.text = String.format("%.2f $", total) // Formatla
        }
        return binding.root
    }

    private fun observeData(kisi_adi: String) {

        try {
            viewModel.sepettekiYemekleriGetir(kisi_adi)
            viewModel.sepetYemekler.observe(viewLifecycleOwner) { yemekler ->
                if (yemekler != null && yemekler.isNotEmpty()) {
                    adapter.updateList(yemekler)
                    binding.cartRV.visibility = View.VISIBLE
                    binding.blankAnimation.visibility = View.GONE
                    binding.blankAnimation.cancelAnimation() // Animasyonu durdur
                    Log.d("CartFragment", "Yemekler: $yemekler")

                } else {
                    binding.cartRV.visibility = View.GONE
                    binding.blankAnimation.visibility = View.VISIBLE
                    binding.blankAnimation.playAnimation() // Animasyonu başlat
                    binding.blankAnimation.loop(true)
                    Log.d("CartFragment", "Sepet boş")
                }
            }
        }catch (e: Exception){
            Log.e("CartFragment", "Hata: ${e.message}")
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriGetir("osman")

    }


}
