package com.example.aifash.ui.users

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {

        val item = voucherItems[position]
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val parsedDate = item?.voucherExpiredDate?.let { inputDateFormat.parse(it) }
        val formattedDate = outputDateFormat.format(parsedDate ?: Date())

        holder.titleTextView.text = "Voucher Touch N Go 50 RM"
        holder.contentTextView.text = String.format("Exp Date: %s", formattedDate)
        when (item?.used) {
            "used" -> holder.statusTextView.text = "Status: used"
            "not_used" -> holder.statusTextView.text = "Status: not used"
            else -> holder.statusTextView.text = "Status: not used"
        }

        holder.button.text = "Use Voucher"
        Glide.with(holder.imageView)
            .load("https://mir-s3-cdn-cf.behance.net/projects/404/2588cc117095563.Y3JvcCwyMjc1LDE3NzksNTAsMA.png")
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return voucherItems.size
    }

    companion object {
        const val TAG = "VoucherUserAdapter"
    }
}

