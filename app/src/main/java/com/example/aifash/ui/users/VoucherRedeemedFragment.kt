package com.example.aifash.ui.users

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.databinding.FragmentVoucherRedeemedBinding
import com.example.aifash.ui.users.home.HomeViewModel
import com.google.gson.Gson

class VoucherRedeemedFragment : Fragment() {
    private var _binding: FragmentVoucherRedeemedBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: VoucherUserAdapter

    private val binding get() = _binding!!

        private fun getUserData(): LoginResponse? {
        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)

        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        return gson.fromJson(loginResponseJson, LoginResponse::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVoucherRedeemedBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = VoucherUserAdapter(emptyList()) // Initially empty
        recyclerView.adapter = adapter

        if (getUserData()?.user?.token?.accessToken != null) {
            getUserData()?.user?.token?.accessToken?.let { homeViewModel.getUserVouchers(it) }
        }

        homeViewModel.voucherData.observe(viewLifecycleOwner) {voucherData ->
            if (voucherData != null) {
                adapter = VoucherUserAdapter(voucherData)
                recyclerView.adapter = adapter
            }
        }

        return binding.root
    }

    companion object {
        private const val TAG = "VoucherRedeemedFragment"
    }
}