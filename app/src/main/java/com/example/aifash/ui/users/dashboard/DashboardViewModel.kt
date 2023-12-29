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
                val productResponse = response.body()
                val productList = productResponse?.voucherItems
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
                Log.d("ProductViewModel", "Response Category: ${productList?.get(0)?.voucherName}")
                Log.d("ProductViewModel", "Response: $productList")
                _productsVoucher.value = productList
                // Handle API error
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}", e)
                // Handle network error
            }
        }
    }

    fun redeemVoucher(userID: Int, voucherID: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Requesting API: $voucherID, $userID")
                val request = RedeemVoucherRequest(userID, voucherID)
                val response = apiService.redeemVoucher(request)
                Log.d(TAG, "Response API: ${response.body()}")

                val productResponse = response.body()
                val productList = productResponse?.data
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
                _productsVoucherUser.value = productList
                // Handle API error
            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}", e)

            }
        }

    }

    companion object {
        private const val TAG = "DashboardViewModel"
    }
}