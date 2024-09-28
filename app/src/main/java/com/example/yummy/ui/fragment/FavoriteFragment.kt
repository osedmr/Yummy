package com.example.yummy.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummy.R
import com.example.yummy.databinding.FragmentFavoriteBinding
import com.example.yummy.ui.adapters.FavoriteAdapter
import com.example.yummy.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteViewModel by viewModels()
        viewModel=tempViewModel

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavoriteBinding.inflate(inflater,container,false)
        binding.close.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.favList.observe(viewLifecycleOwner){
            val adapter= FavoriteAdapter(requireContext(), it.toMutableList(),viewModel)
            binding.favRv.layoutManager= LinearLayoutManager(requireContext())
            binding.favRv.adapter=adapter
        }
        return binding.root
    }


}