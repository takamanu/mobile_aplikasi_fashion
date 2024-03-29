package com.example.aifash.api

import com.example.aifash.*
import com.example.aifash.auth.UserResponse
import com.example.aifash.auth.login.LoginResponse
import com.example.aifash.auth.register.RegisterRequest
import com.example.aifash.auth.register.RegisterResponse
import com.example.aifash.datamodel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @Headers("Accept: application/json; charset=utf-8")
    @POST("fashion")
    fun postFashionItem(
        @Header("Authorization") bearerToken: String,
        @Part("fashion_name") fashionName: RequestBody,
        @Part("fashion_points") fashionPoints: RequestBody,
        @Part attachment: MultipartBody.Part
    ): Call<FashionResponsePost>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("fashion")
    suspend fun getAllFashion(): Response<FashionResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("voucher")
    suspend fun getAllVoucher(): Response<VoucherResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @POST("user-voucher")
    suspend fun redeemVoucher(@Body requestBody: RedeemVoucherRequest): Response<UserVoucherResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("users/{id}")
    suspend fun getCurrentUserData(
        @Path("id") id: Int
    ): Response<UserResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products/search")
    suspend fun searchProducts(@Query("name") query: String): Response<ProductResponse>

//    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
//    @GET("user/{id}")
//    suspend fun getDetailProducts(
//        @Path("id") id: Int?
//    ): Response<ProductResponse>



    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("demands")
    suspend fun getAllDemands(): Response<DemandResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("products")
    suspend fun getAllProducts(): Response<ProductResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
//        @Body request: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("products")
    suspend fun postProducts(
        @Header("x-access-token") tokenAuth: String,
        @Body request: ProductRequest
    ): Response<ProductCreateResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("demands")
    suspend fun postDemand(
        @Header("x-access-token") tokenAuth: String,
        @Body request: DemandRequest
    ): Response<DemandCreateResponse>

    @Headers("Content-Type: application/json; charset=utf-8", "Accept: application/json; charset=utf-8")
    @GET("demands/search")
    suspend fun searchDemands(@Query("name") query: String): Response<DemandResponse>


}
