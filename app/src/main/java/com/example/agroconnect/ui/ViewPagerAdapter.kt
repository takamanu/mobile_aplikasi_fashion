//package com.example.agroconnect.ui
//
//import android.content.Context
//import android.text.SpannableString
//import android.text.Spanned
//import androidx.core.content.res.ResourcesCompat
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import androidx.viewpager.widget.ViewPager
//import com.example.agroconnect.R
//import com.example.agroconnect.auth.CustomTypefaceSpan
//import com.example.agroconnect.auth.login.LoginFragment
//import com.example.agroconnect.auth.register.RegisterFragment
//
//
//class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//    private val mFragmentList = ArrayList<Fragment>()
//    private val mFragmentTitleList = ArrayList<String>()
//
//    override fun getItem(position: Int): Fragment {
//        return mFragmentList[position]
//    }
//
//    override fun getCount(): Int {
//        return mFragmentList.size
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }
//
//    fun addFrag(fragment: Fragment) {
//        mFragmentList.add(fragment)
//        mFragmentTitleList.add("")
//    }
//
//    fun addFrag(fragment: Fragment, title: String) {
//        mFragmentList.add(fragment)
//        mFragmentTitleList.add(title)
//    }
//}
//
