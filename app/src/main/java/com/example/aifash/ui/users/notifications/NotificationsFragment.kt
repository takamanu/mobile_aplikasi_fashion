package com.example.aifash.ui.users.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import androidx.viewpager.widget.ViewPager
import com.example.aifash.databinding.FragmentNotificationsBinding
import com.example.aifash.ui.users.DonatedProductsFragment
import com.example.aifash.ui.users.VoucherRedeemedFragment
import com.google.android.material.tabs.TabLayout

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
        adapter.addFrag(DonatedProductsFragment(), "My Products")
        adapter.addFrag(VoucherRedeemedFragment(), "My Voucher")

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
