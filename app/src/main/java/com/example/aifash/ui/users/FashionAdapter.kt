package com.example.aifash.ui.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aifash.R
import com.example.aifash.datamodel.FashionItem
import com.example.aifash.datamodel.UserFashions

class FashionAdapter(private val fashionItems: List<UserFashions?>) : RecyclerView.Adapter<FashionAdapter.FashionViewHolder>() {

    inner class FashionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvName)
        val contentTextView: TextView = itemView.findViewById(R.id.tvDescription)
        val statusTextView: TextView = itemView.findViewById(R.id.tvDate)
        val imageView: ImageView = itemView.findViewById(R.id.iv_content)
        val carbonView: TextView = itemView.findViewById(R.id.tvCarbon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FashionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_modern, parent, false)
        return FashionViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FashionViewHolder, position: Int) {
        val item = fashionItems[position]
        holder.titleTextView.text = item?.fashionName
        holder.contentTextView.text = "Points achieved: ${item?.fashionPoints.toString()}"
        val carbon = item?.fashionPoints?.toFloat()?.div(100)
        holder.carbonView.text = "Carbon emissions: $carbon kg Co2e"
        if (item != null) {
            when (item.status) {
                "on_process" -> holder.statusTextView.text = "Status: On process"
                "accepted" -> holder.statusTextView.text = "Status: Points added"
                "denied" -> holder.statusTextView.text = "Status: Denied"
            }
        }
        if (item != null) {
            Glide.with(holder.imageView)
                .load(item.fashionUrlImage)
                .into(holder.imageView)
        } // Display the image in the ImageView

    }

    override fun getItemCount(): Int {
        return fashionItems.size
    }




}
