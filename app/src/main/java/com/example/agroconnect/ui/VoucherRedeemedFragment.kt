package com.example.agroconnect.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroconnect.R
import com.example.agroconnect.auth.login.LoginResponse
import com.example.agroconnect.databinding.FragmentDonatedProductsBinding
import com.example.agroconnect.databinding.FragmentSupItemsBinding
import com.example.agroconnect.databinding.FragmentVoucherRedeemedBinding
import com.google.gson.Gson

class VoucherRedeemedFragment : Fragment() {
    private var _binding: FragmentVoucherRedeemedBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVoucherRedeemedBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        Log.d(TAG, "I can access the VoucherReedemedFragment.")

        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userId = loginResponse.user?.id



        // Set up your RecyclerView adapter and data here
        // Example:
        // val adapter = RecyclerViewAdapter(data)
        // recyclerView.adapter = adapter

        return binding.root
    }

    companion object {
        private const val TAG = "VoucherReedemedFragment"
    }
}