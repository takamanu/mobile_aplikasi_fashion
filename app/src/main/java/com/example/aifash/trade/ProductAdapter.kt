package com.example.aifash.trade

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.aifash.databinding.ItemProductBinding
import com.example.aifash.datamodel.FashionItem


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var fashionItems: List<FashionItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val fashionItem = fashionItems[position]
        Log.d("ProductAdapter", "The fashion item is bind: $fashionItem")
        holder.bind(fashionItem)
        Log.d("ProductAdapter", "Holder bind success!2")

    }

    override fun getItemCount(): Int {
        return fashionItems.size
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fashionItem: FashionItem) {
            binding.apply {
//                amountTextView.text = fashionItem.fashionName
//                nameTextView.text = fashionItem.fashionPoints.toString()

                Log.d("ProductAdapter", "Received FashionItem: $fashionItem")
                Log.d("ProductAdapter", "User ID: ${fashionItem.userId}")

//                btnSupItems.setOnClickListener {
//                    val fashionItemJson = Gson().toJson(fashionItem)
//                    val fragment = SupItemsFragment.newInstance(fashionItemJson)
//                    val fragmentManager: FragmentManager? = (itemView.context as? FragmentActivity)?.supportFragmentManager
//                    fragmentManager?.beginTransaction()?.apply {
//                        setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
//                            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                        replace(android.R.id.content, fragment)
//                        addToBackStack(null)
//                        commit()
//                    }
//                }
            }
        }
    }

    fun submitList(fashionItems: List<FashionItem>) {
        this.fashionItems = fashionItems
        Log.d("ProductAdapter", "Nyampe sini")
        notifyDataSetChanged()
    }
}



