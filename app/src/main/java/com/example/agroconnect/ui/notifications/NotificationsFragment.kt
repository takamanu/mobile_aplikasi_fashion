package com.example.agroconnect.ui.notifications

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.agroconnect.auth.login.LoginFragment
import com.example.agroconnect.auth.register.RegisterFragment
import com.example.agroconnect.databinding.ActivityAuthBinding.inflate
import com.example.agroconnect.databinding.FragmentNotificationsBinding
import com.example.agroconnect.trade.ProductViewModel
import com.example.agroconnect.ui.FashionAdapter
import com.example.agroconnect.ui.DonatedProductsFragment
import com.example.agroconnect.ui.VoucherRedeemedFragment
import com.google.android.material.tabs.TabLayout

//class NotificationsFragment : Fragment() {
//
//    private var _binding: FragmentNotificationsBinding? = null
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: FashionAdapter
//    private lateinit var sharedPreferences: SharedPreferences
//    private var viewPager: ViewPager? = null
//    private var tabLayout: TabLayout? = null
//    private val productViewModel: ProductViewModel by viewModels()
//    private val binding get() = _binding!!
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
//        viewPager = binding.viewPager
//        tabLayout = binding.tabLayout
//        setupViewPager()
//
////        val productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
////        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//
////        sharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
////        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
////        val gson = Gson()
////
////        val loginResponse = gson.fromJson(loginResponseJson, LoginResponse::class.java)
////        val userId = loginResponse.user?.id
////
////        recyclerView = binding.recyclerView
////        adapter = FashionAdapter(emptyList()) // Initially empty
////
////        recyclerView.layoutManager = LinearLayoutManager(requireContext())
////        recyclerView.adapter = adapter
//
////        Log.d(TAG, "Ini sudah sampe sini")
////        if (userId != null) {
////            homeViewModel.getCurrentUserData(userId)
////        }
////        Log.d(TAG, "Ini sudah sampe sini 2x")
////
////
////        homeViewModel.userData.observe(viewLifecycleOwner){ userData ->
////
////            Log.d(TAG, "Data to adapter = $userData")
////
////            adapter = FashionAdapter(userData?.fashion)
////            recyclerView.adapter = adapter
////
////
////        }
//
////        if (userId != null) {
////            productViewModel.searchProductsFashion(userId)
////        }
////
////        productViewModel.productsFashion.observe(viewLifecycleOwner) { productsFashion ->
////            val productResponse = productsFashion?.get(0)?.id
////            adapter = FashionAdapter(productsFashion)
////            recyclerView.adapter = adapter
////
////        }
//
//        return binding.root
//    }
//
////    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        super.onViewCreated(view, savedInstanceState)
////
////    }
//
//    private fun setupViewPager() {
//        val adapter = ViewPagerAdapter(childFragmentManager)
//        adapter.addFragment(NotificationsFragment(), "History")
//        adapter.addFragment(DonatedProductsFragment(), "Donated Products")
//        adapter.addFragment(VoucherRedeemedFragment(), "Voucher Redeemed")
//        viewPager!!.adapter = adapter
//        tabLayout!!.setupWithViewPager(viewPager)
//    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        private const val ARG_NOTIFICATION_DATA = "arg_notification_data"
//        private const val TAG = "NotificationsFragment"
//
//    }
//}

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager)

        // Add your fragments
        adapter.addFrag(DonatedProductsFragment(), "Tab2")
        adapter.addFrag(VoucherRedeemedFragment(), "Tab3")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

//        fun addFrag(fragment: Fragment) {
//            mFragmentList.add(fragment)
//            mFragmentTitleList.add("")
//        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "NotificationsFragment"
    }
}
