package com.example.agroconnect.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.agroconnect.MainActivity2
import com.example.agroconnect.R
import com.example.agroconnect.auth.login.LoginResponse
import com.example.agroconnect.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()

        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
        val userId = loginResponse.user?.id

        Log.d(TAG, "Ini sudah sampe sini")
        if (userId != null) {
            homeViewModel.getCurrentUserData(userId)
        }
        Log.d(TAG, "Ini sudah sampe sini 2x")


        homeViewModel.userData.observe(viewLifecycleOwner){ userData ->
            binding.tvNama.text = userData?.name
            binding.tvTotalPointsDonate.text = userData?.points.toString()
            binding.tvTotalProductsDonate.text = userData?.fashion?.size.toString()

        }

        binding.btnMyVoucher.setOnClickListener {
            Toast.makeText(requireContext(), "You have clicked the my voucher button!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddProducts.setOnClickListener {

            val navController = (requireActivity() as MainActivity2).findNavController(R.id.nav_host_fragment_activity_main2)

            navController.popBackStack()
            navController.navigate(R.id.navigation_search)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}