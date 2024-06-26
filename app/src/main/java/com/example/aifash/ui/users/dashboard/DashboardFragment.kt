package com.example.aifash.ui.users.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aifash.databinding.FragmentDashboardBinding
import com.example.aifash.ui.users.VoucherAdapter
import com.example.aifash.ui.users.home.HomeViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VoucherAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        recyclerView = binding.recyclerView
        adapter = VoucherAdapter(emptyList(), dashboardViewModel, requireContext())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        dashboardViewModel.searchProductsVoucher()

        dashboardViewModel.productsVoucher.observe(viewLifecycleOwner) { productsVoucher ->
            adapter = productsVoucher?.let { VoucherAdapter(it, dashboardViewModel, requireContext()) }!!
            recyclerView.adapter = adapter
        }

        dashboardViewModel.productsVoucherUser.observe(viewLifecycleOwner) { productsVoucherUser ->
            if (productsVoucherUser == null) {
                Toast.makeText(requireContext(), "Failed to redeem: Not sufficient points!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "You have redeemed the voucher!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}