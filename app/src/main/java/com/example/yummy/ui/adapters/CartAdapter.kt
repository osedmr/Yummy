package com.example.yummy.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yummy.R
import com.example.yummy.data.entity.SepeteYemekEkleme
import com.example.yummy.databinding.CartCartViewBinding
import com.example.yummy.ui.viewmodel.CartViewModel

class CartAdapter(var mContext: Context, var cartList: MutableList<SepeteYemekEkleme>,var viewModel: CartViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {


    inner class CartViewHolder(var binding: CartCartViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartCartViewBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val cart = cartList[position]
        val t = holder.binding
        t.mealsName.text = cart.yemek_adi
        t.price.text = "${cart.yemek_fiyat} $"

        t.pieceTotal.text = "${cart.yemek_siparis_adet} adet"
        t.totalPrice.text = "${cart.yemek_siparis_adet * cart.yemek_fiyat} $"

        val yemek_resim_adi = cart.yemek_resim_adi
        if (yemek_resim_adi != null) {
            val fullUrl = "http://kasimadalan.pe.hu/yemekler/resimler/$yemek_resim_adi"
            Glide.with(mContext)
                .load(fullUrl)
                .override(200, 200)
                .error(R.drawable.baseline_person_24)
                .into(t.mealsImage)
        } else {
            // Hata durumunda uygun bir mesaj veya fallback işlemi yapabilirsin
            Log.e("CartAdapter", "Resim adı null: $yemek_resim_adi")
        }

        t.delete.setOnClickListener {
            deleteDialog(it, "${cart.yemek_adi} sepetten silmek istiyor musunuz?") {
                val position = cartList.indexOf(cart)
                // Silme işlemi
                sepettenYemekSil(cart.sepet_yemek_id, cart.kullanici_adi)
                // Listedeki öğeyi sil
                cartList.removeAt(position)
                // Adapter'e belirli bir öğenin silindiğini bildir
                notifyItemRemoved(position)
                // Silinen öğeden sonraki item'ları yeniden düzenle
                notifyItemRangeChanged(position, cartList.size)
            }
        }



    }
    fun updateList(newList: List<SepeteYemekEkleme>) {
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }

    fun sepettenYemekSil(sepet_yemek_id:Int,kullanici_adi:String) {
        viewModel.sepettenYemekSil(sepet_yemek_id,kullanici_adi)

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
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }
}