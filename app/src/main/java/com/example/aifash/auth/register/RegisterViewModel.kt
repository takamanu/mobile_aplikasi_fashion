package com.example.aifash.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.Result
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val apiService: ApiService = ApiConfig.createApiService()

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    fun register(username: String, email: String, password: String, role: Int) {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(username, email, password, role)
                Log.d("RegisterViewModel", "Sending register request: $request") // Logging the request
                val response = apiService.register(request)
                Log.d("RegisterViewModel", "$response")
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    _registerResult.value = Result.Success(registerResponse)
                } else {
                    _registerResult.value = Result.Error(Exception("Login failed"))
                }
            } catch (e: Exception) {
                _registerResult.value = Result.Error(e)
            }
        }
    }

}


