package com.example.aifash.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.Result
import com.example.aifash.ResultAsync
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.datamodel.FormatResponse
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val apiService: ApiService = ApiConfig.createApiService()

    private val _loginResult = MutableLiveData<ResultAsync<FormatResponse<LoginUserResponse>>>()
    val loginResult: LiveData<ResultAsync<FormatResponse<LoginUserResponse>>> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
//                val request = LoginRequest(email, password)
                Log.d("LoginViewModel", "Sending login request: $email, $password")
                val response = apiService.login(email, password)
//                val responseBody = response.body()?.user

                Log.d("LoginViewModel", "Response: $response")

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LoginViewModel", "Response Body: $response")


                    if (loginResponse != null) {
                        _loginResult.value = ResultAsync.Success(loginResponse)
                        Log.d("LoginViewModel", "Berhasil kok")
                    } else {
                        Log.d("LoginViewModel", "Response body is null")
                        _loginResult.value = ResultAsync.Error(Exception("Login failed"))
                    }
                } else {
                    Log.d("LoginViewModel", "Response not successful: ${response.code()}")
                    _loginResult.value = ResultAsync.Error(Exception("Login failed"))
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}", e)
                _loginResult.value = ResultAsync.Error(e)
            }
        }

    }

}


