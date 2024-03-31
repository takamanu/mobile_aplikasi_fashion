package com.example.aifash.ui.users

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.databinding.FragmentDonatedProductsBinding
import com.example.aifash.datamodel.ProductFashion
import com.example.aifash.trade.ProductViewModel
import com.example.aifash.trade.SupItemsFragment
import com.example.aifash.ui.users.home.HomeViewModel
import com.google.gson.Gson


class DonatedProductsFragment : Fragment() {

    private var _binding: FragmentDonatedProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FashionAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val productViewModel: ProductViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private fun getUserData(): LoginResponse {
        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)

        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        return gson.fromJson(loginResponseJson, LoginResponse::class.java)
    }

    private fun getProductData(): ProductFashion {
        val productJson = arguments?.getString(SupItemsFragment.ARG_PRODUCT_JSON)

        return Gson().fromJson(productJson, ProductFashion::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonatedProductsBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FashionAdapter(emptyList())
        recyclerView.adapter = adapter

        getUserData().user?.token?.accessToken?.let { homeViewModel.getUserProducts(it) }

        homeViewModel.productData.observe(viewLifecycleOwner) { productData ->
            if (productData != null) {
//                adapter.submitList(productsFashion)
                adapter = FashionAdapter(productData)
                recyclerView.adapter = adapter
            }
        }



//        productViewModel.productsFashion.observe(viewLifecycleOwner) { productsFashion ->
//            if (productsFashion != null) {
////                adapter.submitList(productsFashion)
//                adapter = productsFashion?.let { FashionAdapter(it) }!!
//                recyclerView.adapter = adapter
//            }
//        }
//
        return binding.root
    }

    companion object {
        private const val TAG = "DonatedProductsFragment"
    }
}