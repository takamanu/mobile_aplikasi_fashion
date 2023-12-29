package com.example.aifash.trade

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.datamodel.ProductLearningResponse
import com.example.aifash.datamodel.ProductMachineResponse

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = ApiConfig.createApiServiceML()

    private val _products = MutableLiveData<List<List<ProductLearningResponse>>?>()
    val products: MutableLiveData<List<List<ProductLearningResponse>>?> get() = _products

    private val _locations = MutableLiveData<ProductMachineResponse?>()
    val locations: MutableLiveData<ProductMachineResponse?> get() = _locations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

//    fun getAllProductsML(query: List<String>) {
//        viewModelScope.launch {
//            try {
//                val response = apiService.getProductsMLQuery(query)
//                val productLearningResponse = response.body()
//                if (response.isSuccessful) {
//                    _locations.value = productLearningResponse
//                    Log.d("MapsViewModel", "Response: $productLearningResponse")
//                } else {
//                    _error.value = "Error: ${response.message()}"
//                }
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//            }
//        }
//    }
}
