package com.example.aifash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aifash.databinding.ActivityMainBinding
import com.example.aifash.trade.*
import com.google.android.material.bottomnavigation.BottomNavigationView

//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var sessionPreferences: SharedPreferences
//    private lateinit var networkConnectivityWatcher: NetworkConnectivityWatcher
//    private lateinit var productViewModel: ProductViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        supportActionBar?.hide()
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        setContentView(binding.root)
//
//        binding.tvGreeting.text = "Selamat pagi,";
//        binding.tvUsername.text = "Fauzan Ganteng";
//
//        binding.ivAvatar.setOnClickListener {
//            showLogoutDialog()
//
//        }
//
//        binding.tvTradehistory.setOnClickListener{
//            val tradeActivity = Intent(this, TradeActivity::class.java)
//            startActivity(tradeActivity)
//        }
//
//        binding.tvFindpotential.setOnClickListener{
//            val findPotentialActivity = Intent(this, PotentialActivity::class.java)
//            startActivity(findPotentialActivity)
//        }
//
//        binding.tvFinddemand.setOnClickListener{
//
//            val dialog = Dialog(this)
//            val dialogBinding = ModalLayoutFindBinding.inflate(layoutInflater)
//            dialog.setContentView(dialogBinding.root)
//
//
//            dialogBinding.btnFindSupplies.setOnClickListener {
//                val searchActivity = Intent(this, SearchActivity::class.java)
//                searchActivity.putExtra("isDemand", false)
//                startActivity(searchActivity)
//                dialog.dismiss()
//            }
//
//            dialogBinding.btnFindDemand.setOnClickListener {
//                val searchActivity = Intent(this, SearchActivity::class.java)
//                searchActivity.putExtra("isDemand", true)
//                startActivity(searchActivity)
//                dialog.dismiss()
//            }
//
//            dialog.show()
//        }
//
////        binding.tvFindsupplies.setOnClickListener{
////            val searchActivity = Intent(this, SearchActivity::class.java)
////            searchActivity.putExtra("isDemand", false)
////            startActivity(searchActivity)
////        }
//
//        binding.tvCreate.setOnClickListener {
//            val dialog = Dialog(this)
//            val dialogBinding = ModalLayoutBinding.inflate(layoutInflater)
//            dialog.setContentView(dialogBinding.root)
//
//
//            dialogBinding.btnCreateSupplies.setOnClickListener {
//                val demandIntent = Intent(this, DemandActivity::class.java)
//                startActivity(demandIntent)
//                dialog.dismiss()
//            }
//
//            dialogBinding.btnCreateDemand.setOnClickListener {
//                val suppliesIntent = Intent(this, SuppliesActivity::class.java)
//                startActivity(suppliesIntent)
//
//                dialog.dismiss()
//            }
//
//            dialog.show()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        networkConnectivityWatcher.stopWatchingConnectivity()
//    }
//
//    private fun showLogoutDialog() {
//        MaterialDialog(this).show {
//            customView(R.layout.dialog_logout) // Replace with your custom dialog layout
//
//            // Handle dialog buttons
//            positiveButton(R.string.logout) {
//                sessionPreferences.edit().remove("loginResponse").apply()
//                navigateToAuthActivity()
//            }
//            negativeButton(android.R.string.cancel)
//        }
//    }
//
//    private fun navigateToAuthActivity() {
//        val intent = Intent(this, AuthActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    private fun isSessionActive(): Boolean {
//        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
//        return loginResponseJson != null
//    }
//
//
//}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_admin, R.id.navigation_search_fashion, R.id.navigation_search_vouchers, R.id.navigation_configs
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

