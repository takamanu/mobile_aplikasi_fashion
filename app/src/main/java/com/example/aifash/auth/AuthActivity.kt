package com.example.aifash.auth;

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.aifash.MainActivity
import com.example.aifash.MainActivity2
import com.example.aifash.R
import com.example.aifash.auth.login.LoginFragment
import com.example.aifash.auth.register.RegisterFragment
import com.example.aifash.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayout

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE)

        if (isSessionActive()) {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
            return
        }

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val pagerAdapter = AuthPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun isSessionActive(): Boolean {
        val loginResponseJson = sharedPreferences.getString("loginResponse", null)
        return loginResponseJson != null
    }

}
