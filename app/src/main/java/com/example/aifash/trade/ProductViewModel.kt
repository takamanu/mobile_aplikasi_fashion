package com.example.aifash.trade


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.aifash.api.ApiConfig
import com.example.aifash.api.ApiService
import com.example.aifash.datamodel.*
import com.example.aifash.ui.users.Utils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONTokener

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = ApiConfig.createApiService()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _productsFashion = MutableLiveData<List<FashionItem>?>()
    val productsFashion: MutableLiveData<List<FashionItem>?> get() = _productsFashion

    private val _demands = MutableLiveData<List<Demand>>()
    val demands: MutableLiveData<List<Demand>> get() = _demands

    private val _productData = MutableLiveData<Product?>()
    val productData: MutableLiveData<Product?> get() = _productData

    private val _demandData = MutableLiveData<Demand?>()
    val demandData: MutableLiveData<Demand?> get() = _demandData

    private val _searchResultEmpty = MutableLiveData<Boolean>()
    val searchResultEmpty: LiveData<Boolean> get() = _searchResultEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProducts(query)
                val productResponse = response.body()
                val productList = productResponse!!.data
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
//                Log.d("ProductViewModel", "Response Category: ${productList[0].categoryId}")
//                Log.d("ProductViewModel", "Response: $productList")
                if (productList.isEmpty()) {
                    _searchResultEmpty.value = true
                    _products.value = productList
                } else {
                    _searchResultEmpty.value = false
                    _products.value = productList
                }
                    // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun searchProductsFashion(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllFashion()
                Log.d(TAG, "Product List: ${response.body()}")
                val productResponse = response.body()
                val productList = productResponse?.fashionItems
                if (query != "") {
                    val filteredProductList = productList?.filter { it.fashionName.contains(query, ignoreCase = true) }
                    Log.d(TAG, "Filtered Product List: $filteredProductList")
                    _productsFashion.value = filteredProductList
                } else {
                    _productsFashion.value = productList
                }

                // Filter the product list based on the query


            } catch (e: Exception) {
                // Handle network error
            }
        }
    }


    fun uploadImage(

        productName: String,
        productPoints: Int,
        imageMultipart: MultipartBody.Part,
        token: String,
        callback: Utils.ApiCallbackString
    ) {
        Log.d(TAG, "Data sent: $imageMultipart")
        _isLoading.value = true
        val bearerToken = "Bearer $token"

        val service = ApiConfig.createApiService().postFashionItem(bearerToken, productName.toRequestBody("text/plain".toMediaTypeOrNull()), productPoints.toString().toRequestBody("text/plain".toMediaTypeOrNull()), imageMultipart)
        Log.d(TAG, "Data receive: $service")

        service.enqueue(object : retrofit2.Callback<FashionResponsePost> {
            override fun onResponse(
                call: retrofit2.Call<FashionResponsePost>,
                response: retrofit2.Response<FashionResponsePost>
            ) {
                Log.d(TAG, "Response: $response")
                Log.d(TAG, "Response Body: ${response.body()}")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback.onResponse(response.body() != null, SUCCESS)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                    val jsonObject =
                        JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
                    val message = jsonObject.getString("message")
                    callback.onResponse(false, message)
                }
            }

            override fun onFailure(call: retrofit2.Call<FashionResponsePost>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                callback.onResponse(false, t.message.toString())
            }
        })
    }




    fun postProducts(selectedCategory: Int, name: String, amount: String, cropDate: String, expDate: String, location: String, tokenAuth: String) {
        viewModelScope.launch {
            try {
                val request = ProductRequest(
                    name = name,
                    amount = amount.toInt(),
                    location = location,
                    crop_date = cropDate,
                    estimate_exp = expDate,
                    category_id = selectedCategory
                )
                Log.d("ProductViewModel", "Request Add Product: $request, Token: $tokenAuth")

                val response = apiService.postProducts(tokenAuth, request)
                Log.d("ProductViewModel", "Response Add Product: $response")
                val productData = response.body()?.data
                _productData.value = productData
                Log.d("ProductViewModel", "The request should be transferred to Activity")


            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error during API call: ${e.message}", e)
                // Handle the exception
                _productData.value = null
                showToast("Failed to add product.")
            }
        }
    }

    fun postDemand(selectedCategory: Int, name: String, amount: String, location: String, tokenAuth: String) {
        viewModelScope.launch {
            try {
                val request = DemandRequest(
                    name = name,
                    amount = amount.toInt(),
                    location = location,
                    category_id = selectedCategory
                )
                Log.d("ProductViewModel", "Request Add Demand: $request, Token: $tokenAuth")
                val response = apiService.postDemand(tokenAuth, request)
                Log.d("ProductViewModel", "Response Add Demand: $response")
                val demandData = response.body()?.data
                _demandData.value = demandData
                Log.d("ProductViewModel", "The request should be transferred to Activity")


            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error during API call: ${e.message}", e)
                // Handle the exception
                _demandData.value = null
                showToast("Failed to add Demand.")
            }
        }
    }

    fun searchProductsByCategory(query: String, categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProducts(query)
                val productResponse = response.body()
                val productList = productResponse!!.data
                val filteredList = productList.filter { it.categoryId == categoryId }
                if (filteredList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _products.value = filteredList
                } else {
                    _searchResultEmpty.value = false
                    _products.value = filteredList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun searchDemand(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchDemands(query)
                val demandResponse = response.body()
                val demandList = demandResponse!!.data
//                val fullProductResponse = apiService.getDetailProducts(productList[0].categoryId)
                Log.d("ProductViewModel", "Response Category: ${demandList?.get(0)?.categoryId}")
                Log.d("ProductViewModel", "Response: $demandList")
                if (demandList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _demands.value = demandList
                } else {
                    _searchResultEmpty.value = false
                    _demands.value = demandList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun searchDemandByCategory(query: String, categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.searchDemands(query)
                val demandResponse = response.body()
                val demandList = demandResponse!!.data
                val filteredList = demandList.filter { it.categoryId == categoryId }
                if (filteredList.isNullOrEmpty()) {
                    _searchResultEmpty.value = true
                    _demands.value = filteredList
                } else {
                    _searchResultEmpty.value = false
                    _demands.value = filteredList
                }
                // Handle API error
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }

    fun getAllDemand(categoryId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getAllDemands()
                val demandAllResponse = response.body()
                val demandAllList = demandAllResponse!!.data
                val filteredList = demandAllList.filter {
                    it.categoryId == categoryId && it.userId == userId
                }

                Log.d("ProductViewModel", "Fetch user $userId and category $categoryId")
                if (filteredList.isNullOrEmpty()) {
                    _demands.value = filteredList
                } else {
                    _demands.value = filteredList
                }


            } catch (e: Exception) {

            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val responseProducts = apiService.getAllProducts()
                val productAllResponse = responseProducts.body()
                val productsAllList = productAllResponse!!.data
//                val filteredListProduct = productsAllList.filter { it.userId == userId }
                if (productsAllList.isNullOrEmpty()) {
                    _products.value = productsAllList
                } else {
                    _products.value = productsAllList
                    val filteredListDemandSize = productsAllList.size
                }



            } catch (e: Exception) {
            }
        }
    }

    fun getNumberAllDemand(userId: Int) {
        viewModelScope.launch {
            try {
                val responseDemand = apiService.getAllDemands()
                val demandAllResponse = responseDemand.body()
                val demandAllList = demandAllResponse!!.data
                val filteredListDemand = demandAllList.filter {
                    it.userId == userId
                }


                Log.d("ProductViewModel", "Fetch user $userId")
                if (filteredListDemand.isNullOrEmpty()) {
                    _demands.value = filteredListDemand
                } else {
                    _demands.value = filteredListDemand
                    val filteredListDemandSize = filteredListDemand.size
                }


            } catch (e: Exception) {

            }
        }
    }

    fun getNumberAllProducts(userId: Int) {
        viewModelScope.launch {
            try {
                val responseProducts = apiService.getAllProducts()
                val productAllResponse = responseProducts.body()
                val productsAllList = productAllResponse!!.data
                val filteredListProduct = productsAllList.filter { it.userId == userId }
                if (filteredListProduct.isNullOrEmpty()) {
                    _products.value = filteredListProduct
                } else {
                    _products.value = filteredListProduct
                    val filteredListDemandSize = filteredListProduct.size
                }



            } catch (e: Exception) {
        }

        }

    }

    private fun showToast(message: String) {
        val context = getApplication<Application>()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ProductViewModel"
        private const val SUCCESS = "Success, your image has been uploaded!"
    }


}



