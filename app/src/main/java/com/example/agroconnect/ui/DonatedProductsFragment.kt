package com.example.agroconnect.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.agroconnect.R
import com.example.agroconnect.auth.login.LoginResponse
import com.example.agroconnect.databinding.FragmentDonatedProductsBinding
import com.example.agroconnect.databinding.FragmentNotificationsBinding
import com.example.agroconnect.databinding.FragmentSupItemsBinding
import com.example.agroconnect.trade.ProductViewModel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson


class DonatedProductsFragment : Fragment() {

    private var _binding: FragmentDonatedProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FashionAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonatedProductsBinding.inflate(inflater, container, false)

        Log.d(TAG, "I can access the DonatedProductsFragment.")

        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userId = loginResponse.user?.id

//        binding.textView7.visibility = View.VISIBLE
//        binding.textView7.text = "Ini muncul bro"

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FashionAdapter(emptyList()) // Initially empty
        recyclerView.adapter = adapter

        if (userId != null) {
            productViewModel.searchProductsFashion(userId)
        }

        productViewModel.productsFashion.observe(viewLifecycleOwner) { productsFashion ->
            if (productsFashion != null) {
//                adapter.submitList(productsFashion)
                adapter = productsFashion?.let { FashionAdapter(it) }!!
                recyclerView.adapter = adapter
            }
        }

        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
////        _binding = null
//    }

    companion object {
        private const val TAG = "DonatedProductsFragment"
    }
}