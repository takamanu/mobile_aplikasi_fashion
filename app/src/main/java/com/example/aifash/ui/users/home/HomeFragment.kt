package com.example.aifash.ui.users.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.auth0.android.jwt.JWT
import com.example.aifash.MainActivity2
import com.example.aifash.R
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var sharedPreferences: SharedPreferences
    private val binding get() = _binding!!

    private fun getTokenJWT(): String? {
        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)

        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        val gson = Gson()
        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)

        return loginResponse.user?.token?.accessToken
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val token = getTokenJWT()

        if (token != null) {
            homeViewModel.getCurrentUserData(token)
        }

        homeViewModel.userData.observe(viewLifecycleOwner){ userData ->
            binding.tvNama.text = userData?.name
            binding.tvTotalPointsDonate.text = String.format("%d points ",userData?.points)
//            binding.tvTotalProductsDonate.text = "${userData?.fashion?.size.toString()} items"
//            Glide.with(this)
//                .load("https://static.thenounproject.com/png/2366460-200.png")
//                .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                .into(binding.imageView2)

        }

        homeViewModel.userDataCarbon.observe(viewLifecycleOwner){userDataCarbon ->
            val carbon = userDataCarbon?.toFloat()?.div(100)
            binding.tvTotalCarbon.text = String.format("%.2f kg Co2e", carbon)

            val treePlanted = carbon?.div(22)
            binding.tvTotalTreePlanted.text = String.format("equivalent to %.2f tree planted", treePlanted)


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

        binding.btnMyVoucher.setOnClickListener {
            val navController = (requireActivity() as MainActivity2).findNavController(R.id.nav_host_fragment_activity_main2)
            navController.popBackStack()


            navController.navigate(R.id.navigation_notifications, Bundle().apply {
                putInt("tabIndex", 1)
            })

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