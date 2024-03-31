package com.example.aifash.auth

import android.text.SpannableString
import android.text.Spanned
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.aifash.R
import com.example.aifash.auth.login.LoginFragment
import com.example.aifash.auth.register.RegisterFragment

class AuthPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var viewPager: ViewPager

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> LoginFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val context = viewPager.context
        val typeface = ResourcesCompat.getFont(context, R.font.nunito)
        val spannableString = SpannableString(
            when (position) {
                0 -> "Login"
                1 -> "Register"
                else -> null
            }
        )
        spannableString.setSpan(CustomTypefaceSpan(typeface), 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}