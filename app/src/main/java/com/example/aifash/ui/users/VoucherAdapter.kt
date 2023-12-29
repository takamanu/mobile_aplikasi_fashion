package com.example.aifash.ui.users

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aifash.R
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.datamodel.VoucherItem
import com.example.aifash.ui.users.dashboard.DashboardViewModel
import com.google.gson.Gson

class VoucherAdapter(
    private val voucherItems: List<VoucherItem>,
    private val viewModel: DashboardViewModel,
    private val context: Context
) : RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>() {
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
        sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userId = loginResponse.user?.id

        val item = voucherItems[position]
        holder.titleTextView.text = item.voucherName
        holder.contentTextView.text = "Redeem for ${item.voucherPrice} points"
        holder.statusTextView.text = item.voucherCode
        holder.button.setOnClickListener {
            item.iD?.let { it1 ->
                if (userId != null) {
                    viewModel.redeemVoucher(userId, it1)
                }
            }
        }
        Glide.with(holder.imageView)
            .load(item.voucherUrlImage)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return voucherItems.size
    }
}

