package com.example.aifash.ui.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import kotlinx.coroutines.launch

class AddProductsViewModel : ViewModel() {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _productData = MutableLiveData<String>()
    val productData: LiveData<String> = _productData
    fun setProductData(data: String) {
        _productData.value = data
        Log.d("AddProductsViewModel", "This is the data sent: $data")

    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                Log.d("AddProductsViewModel", "Nyampe sini")

                val response = apiService.getAllFashion()
                Log.d("AddProductsViewModel", "Ini isi response: $response")
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    if (categoryResponse != null) {
                        val categoryList = categoryResponse.fashionItems
                        Log.d("AddProductsViewModel", "Ini isi data: $categoryList")
                    } else {
                        Log.e("AddProductsViewModel", "Ini isi data: $categoryResponse")
                    }
                } else {
                    Log.e("AddProductsViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("AddProductsViewModel", "Exception: ${e.message}")
            }
        }
    }


}
