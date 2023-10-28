package com.example.agroconnect

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.agroconnect.auth.AuthActivity
import com.example.agroconnect.databinding.ActivityMainBinding
import com.example.agroconnect.databinding.ModalLayoutBinding
import com.example.agroconnect.databinding.ModalLayoutFindBinding
import com.example.agroconnect.trade.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionPreferences: SharedPreferences
    private lateinit var networkConnectivityWatcher: NetworkConnectivityWatcher
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(binding.root)

        binding.tvGreeting.text = "Selamat pagi,";
        binding.tvUsername.text = "Fauzan Ganteng";

        binding.ivAvatar.setOnClickListener {
            showLogoutDialog()

        }

        binding.tvTradehistory.setOnClickListener{
            val tradeActivity = Intent(this, TradeActivity::class.java)
            startActivity(tradeActivity)
        }

        binding.tvFindpotential.setOnClickListener{
            val findPotentialActivity = Intent(this, PotentialActivity::class.java)
            startActivity(findPotentialActivity)
        }

        binding.tvFinddemand.setOnClickListener{

            val dialog = Dialog(this)
            val dialogBinding = ModalLayoutFindBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)


            dialogBinding.btnFindSupplies.setOnClickListener {
                val searchActivity = Intent(this, SearchActivity::class.java)
                searchActivity.putExtra("isDemand", false)
                startActivity(searchActivity)
                dialog.dismiss()
            }

            dialogBinding.btnFindDemand.setOnClickListener {
                val searchActivity = Intent(this, SearchActivity::class.java)
                searchActivity.putExtra("isDemand", true)
                startActivity(searchActivity)
                dialog.dismiss()
            }

            dialog.show()
        }

//        binding.tvFindsupplies.setOnClickListener{
//            val searchActivity = Intent(this, SearchActivity::class.java)
//            searchActivity.putExtra("isDemand", false)
//            startActivity(searchActivity)
//        }

        binding.tvCreate.setOnClickListener {
            val dialog = Dialog(this)
            val dialogBinding = ModalLayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)


            dialogBinding.btnCreateSupplies.setOnClickListener {
                val demandIntent = Intent(this, DemandActivity::class.java)
                startActivity(demandIntent)
                dialog.dismiss()
            }

            dialogBinding.btnCreateDemand.setOnClickListener {
                val suppliesIntent = Intent(this, SuppliesActivity::class.java)
                startActivity(suppliesIntent)

                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectivityWatcher.stopWatchingConnectivity()
    }

    private fun showLogoutDialog() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_logout) // Replace with your custom dialog layout

            // Handle dialog buttons
            positiveButton(R.string.logout) {
                sessionPreferences.edit().remove("loginResponse").apply()
                navigateToAuthActivity()
            }
            negativeButton(android.R.string.cancel)
        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isSessionActive(): Boolean {
        val loginResponseJson = sessionPreferences.getString("loginResponse", null)
        return loginResponseJson != null
    }


}

