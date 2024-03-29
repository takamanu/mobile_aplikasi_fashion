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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVoucherRedeemedBinding.inflate(inflater, container, false)

        Log.d(TAG, "I can access the ${TAG}.")


        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
//        val userId = loginResponse.user?.id
        val userId = 1

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = VoucherUserAdapter(emptyList()) // Initially empty
        recyclerView.adapter = adapter

//        if (userId != null) {
        homeViewModel.getCurrentUserData(userId)
//        }

        homeViewModel.userData.observe(viewLifecycleOwner) {userData ->
            if (userData != null) {
//                val sentUserData = userData.userVouchers
//                adapter = sentUserData?.let { VoucherUserAdapter(it) }!!
                recyclerView.adapter = adapter
            }
        }


        // Set up your RecyclerView adapter and data here
        // Example:
        // val adapter = RecyclerViewAdapter(data)
        // recyclerView.adapter = adapter

        return binding.root
    }

    fun fmt(msg: String): Any {
        println(msg)
        return msg
    }

    companion object {
        private const val TAG = "VoucherRedeemedFragment"
    }
}