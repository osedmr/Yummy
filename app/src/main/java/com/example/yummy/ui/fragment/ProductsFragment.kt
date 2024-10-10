package com.example.yummy.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yummy.R
import com.example.yummy.databinding.FragmentProductsBinding
import com.example.yummy.ui.adapters.MealsAdapter
import com.example.yummy.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: ProductsViewModel by viewModels()
        viewModel = tempViewModel
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding=FragmentProductsBinding.inflate(inflater,container,false)
        binding.address.playAnimation()
        viewModel.meals.observe(viewLifecycleOwner) { mealsList ->
            if (mealsList != null) {
                binding.mealsRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                val adapter = MealsAdapter(viewModel)
                adapter.submitList(mealsList)
                binding.mealsRv.adapter = adapter


            } else {
                // Handle the null case (maybe show an empty view or a message)
            }
        }


        binding.sortButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.sort_by_name -> {
                        viewModel.sortByName()  // İsimle sıralama fonksiyonunu çağır
                        true
                    }
                    R.id.sort_by_price -> {
                        viewModel.sortByPrice()  // Fiyatla sıralama fonksiyonunu çağır
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
        binding.filterButton.setOnClickListener {
            val filterFragment = PriceFilterBottomSheetFragment().apply {
                onPriceSelected = { selectedMinPrice ->
                    viewModel.filterByPriceRange(selectedMinPrice) // Seçilen fiyatı ViewModel'e gönder
                }
                onResetFilters = {
                    viewModel.resetFilters() // Filtreleri sıfırla
                }
            }
            filterFragment.show(parentFragmentManager, "PriceFilter")
        }

        return binding.root
    }


}