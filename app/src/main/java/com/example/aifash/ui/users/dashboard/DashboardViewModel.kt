package com.example.aifash.ui.users.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.datamodel.RedeemVoucherRequest
import com.example.aifash.datamodel.UserVoucherItem
import com.example.aifash.datamodel.VoucherItem
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val apiService: ApiService = ApiConfig.createApiService()

    private val _productsVoucher = MutableLiveData<List<VoucherItem>?>()
    val productsVoucher: MutableLiveData<List<VoucherItem>?> get() = _productsVoucher

    private val _productsVoucherUser = MutableLiveData<UserVoucherItem?>()
    val productsVoucherUser: MutableLiveData<UserVoucherItem?> get() = _productsVoucherUser


    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun searchProductsVoucher() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllVoucher()
                _productsVoucher.value = response.body()?.data
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}", e)
            }
        }
    }

    fun redeemVoucher(token: String, voucherID: Int) {
        viewModelScope.launch {
            try {
                val bearerToken = String.format("Bearer %s", token)
                val response = apiService.redeemVoucher(bearerToken, voucherID)
                Log.d(TAG, "Response API: ${response.body()}")

                if (response.isSuccessful) {
                    _productsVoucherUser.value = response.body()?.data
                } else {
//                    _productsVoucherUser.value = null
                    Log.d(TAG, "Error: failed to claim voucher")
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}", e)

            }
        }

    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}