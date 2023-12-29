package com.example.aifash.auth.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: Int
)
