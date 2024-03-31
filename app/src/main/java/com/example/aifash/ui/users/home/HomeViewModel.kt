package com.example.aifash.ui.users.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.auth.User
import com.example.aifash.datamodel.ProductFashion
import com.example.aifash.datamodel.UserFashions
import com.example.aifash.datamodel.UserVoucherItem
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> get() = _userData

    private val _productData = MutableLiveData<List<UserFashions>?>()
    val productData: MutableLiveData<List<UserFashions>?> get() = _productData

    private val _voucherData = MutableLiveData<List<UserVoucherItem>?>()
    val voucherData: MutableLiveData<List<UserVoucherItem>?> get() = _voucherData

    private val _userDataCarbon = MutableLiveData<Int?>()
    val userDataCarbon: MutableLiveData<Int?> get() = _userDataCarbon

    fun getCurrentUserData(token: String) {
        viewModelScope.launch {
            try {
                val bearerToken = String.format("Bearer %s", token)
                val response = apiService.getCurrentUserData(bearerToken)
                val userDataResponse = response.body()?.data
                Log.d(TAG, "Response API: $userDataResponse")

                if (response.isSuccessful) {
                    _userData.value = userDataResponse
                    _userDataCarbon.value = userDataResponse?.points
                } else {
                    _userData.value = null
                    _userDataCarbon.value = 0
                    Log.d(TAG, "Error: Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
            }

        }
    }

    fun getUserProducts(token: String) {
        viewModelScope.launch {
            try {
                val bearerToken = String.format("Bearer %s", token)
                val response = apiService.getUserProducts(bearerToken)

                if (response.isSuccessful) {
                    _productData.value = response.body()?.data
                } else {
                    _productData.value = null
                    Log.d(TAG, "Error: Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
            }

        }
    }

    fun getUserVouchers(token: String) {
        viewModelScope.launch {
            try {
                val bearerToken = String.format("Bearer %s", token)
                val response = apiService.getUserVouchers(bearerToken)

                if (response.isSuccessful) {
                    _voucherData.value = response.body()?.data
                } else {
                    _voucherData.value = null
                    Log.d(TAG, "Error: Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
            }

        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}