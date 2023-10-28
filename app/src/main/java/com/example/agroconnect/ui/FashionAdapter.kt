package com.example.agroconnect.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agroconnect.R
import com.example.agroconnect.datamodel.FashionItem

class FashionAdapter(private val fashionItems: List<FashionItem>) : RecyclerView.Adapter<FashionAdapter.FashionViewHolder>() {

    inner class FashionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_status_history)
        val imageView: ImageView = itemView.findViewById(R.id.fashionImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FashionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fashion, parent, false)
        return FashionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FashionViewHolder, position: Int) {
        val item = fashionItems?.get(position)
        holder.titleTextView.text = item?.fashionName
        holder.contentTextView.text = "Points achieved: ${item?.fashionPoints.toString()}"
        if (item != null) {
            when (item.status) {
                false -> holder.statusTextView.text = "Status: On process"
                true -> holder.statusTextView.text = "Status: Points added"
            }
        }
        if (item != null) {
            Glide.with(holder.imageView)
                .load(item.fashionUrlImage)
                .into(holder.imageView)
        } // Display the image in the ImageView

    }

    override fun getItemCount(): Int {
        return fashionItems!!.size
    }




}
