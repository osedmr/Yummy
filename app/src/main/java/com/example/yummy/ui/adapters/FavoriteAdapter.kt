package com.example.yummy.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummy.R
import com.example.yummy.data.entity.Favorite
import com.example.yummy.databinding.CartCartViewBinding
import com.example.yummy.databinding.FavCartViewBinding
import com.example.yummy.databinding.FragmentFavoriteBinding
import com.example.yummy.ui.viewmodel.FavoriteViewModel

class FavoriteAdapter(var mContext: Context, var favoriteList: MutableList<Favorite>, var viewModel: FavoriteViewModel): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

      inner class FavoriteViewHolder(var binding: FavCartViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =  FavCartViewBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favoriteList[position]
        val t = holder.binding
        t.mealsName.text = favorite.yemek_adi
        t.price.text = "${favorite.yemek_fiyat} $"
        val yemek_resim_adi = favorite.yemek_resim
        val fullUrl = "http://kasimadalan.pe.hu/yemekler/resimler/$yemek_resim_adi"
        Glide.with(mContext)
            .load(fullUrl)
            .override(200, 200)
            .error(R.drawable.baseline_person_24)
            .into(t.mealsImage)


        t.delete.setOnClickListener {

            deleteDialog(it, "${favorite.yemek_adi} favorilerden silmek istiyor musunuz?") {
                val position = favoriteList.indexOf(favorite)
                viewModel.deleteFav(favorite.yemek_id)
                Toast.makeText(mContext, "${favorite.yemek_adi} silindi", Toast.LENGTH_SHORT).show()
                favoriteList.removeAt(position)
                // Adapter'e belirli bir öğenin silindiğini bildir
                notifyItemRemoved(position)
            }

        }

    }

    fun deleteDialog(view: View, message:String, onDeleteConfirmed:() -> Unit){
        val builder= AlertDialog.Builder(view.context)
        builder.setTitle("Silme İşlemi")
        builder.setMessage(message)
        builder.setPositiveButton("Evet"){dialog,_ ->
            onDeleteConfirmed()
            dialog.dismiss()
        }
        builder.setNegativeButton("Hayır"){dialog,_ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}