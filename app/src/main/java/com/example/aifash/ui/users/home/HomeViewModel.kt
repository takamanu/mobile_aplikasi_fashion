package com.example.aifash.ui.users.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.auth.User
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _userData = MutableLiveData<User?>()
    val userData: MutableLiveData<User?> get() = _userData

    private val _userDataCarbon = MutableLiveData<Int?>()
    val userDataCarbon: MutableLiveData<Int?> get() = _userDataCarbon

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
                val totalPoints = calculateTotalFashionPoints(userDataResponse)
                Log.d(TAG, "This is the total points gained: $totalPoints")

                if (response.isSuccessful) {
                    _userData.value = userDataResponse
                    _userDataCarbon.value = totalPoints
                } else {
                    _userData.value = null
                    _userDataCarbon.value = 0
                    Log.d(TAG, "Error: Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

                Log.d(TAG, "Fetch user $userId")
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}", e)
            }

        }
    }

    fun calculateTotalFashionPoints(userDataResponse: User?): Int {
        var totalFashionPoints = 0

        userDataResponse?.fashion?.let { fashionItems ->
            for (fashionItem in fashionItems) {
                fashionItem?.fashionPoints?.let {
                    totalFashionPoints += it
                }
            }
        }

        return totalFashionPoints
    }


    companion object {
        private const val TAG = "HomeViewModel"
    }
}