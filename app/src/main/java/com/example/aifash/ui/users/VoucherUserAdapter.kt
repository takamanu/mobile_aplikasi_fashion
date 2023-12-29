package com.example.aifash.ui.users

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aifash.R
import com.example.aifash.datamodel.UserVoucherItem

class VoucherUserAdapter(
    private val voucherItems: List<UserVoucherItem?>
) : RecyclerView.Adapter<VoucherUserAdapter.VoucherViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    inner class VoucherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_status_history)
        val imageView: ImageView = itemView.findViewById(R.id.fashionImage)
        val button: Button = itemView.findViewById(R.id.btn_reedem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return VoucherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {

        val item = voucherItems[position]
        holder.titleTextView.text = item?.voucher?.voucherName
        holder.contentTextView.text = "Redeem for ${item?.voucher?.voucherPrice} points"
        when (item?.used) {
            true -> holder.statusTextView.text = "Status: used"
            false -> holder.statusTextView.text = "Status: not used"
            else -> holder.statusTextView.text = "Status: not used"
        }
        Log.d(TAG, "Check image URL: ${item?.voucher?.voucherUrlImage}")
//        holder.button.setOnClickListener {}
        Glide.with(holder.imageView)
            .load(item?.voucher?.voucherUrlImage)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return voucherItems.size
    }

    companion object {
        const val TAG = "VoucherUserAdapter"
    }
}

