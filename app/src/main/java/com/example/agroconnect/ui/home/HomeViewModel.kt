package com.example.agroconnect.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agroconnect.api.ApiConfig
import com.example.agroconnect.api.ApiService
import com.example.agroconnect.auth.User
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> get() = _userData

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getCurrentUserData(userId: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Requesting API: Fetch user $userId")
                val response = apiService.getCurrentUserData(userId)
                val responseBody = response.body()
                val userDataResponse = responseBody?.user
                Log.d(TAG, "Response API: $userDataResponse")

                if (response.isSuccessful) {
                    _userData.value = userDataResponse
                } else {
                    _userData.value = null
                    Log.d(TAG, "Error: Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

                Log.d(TAG, "Fetch user $userId")
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
            }

        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}