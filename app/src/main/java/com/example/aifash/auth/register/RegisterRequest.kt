package com.example.aifash.auth.register

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class RegisterRequest(
    @SerializedName("name") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: String,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("phone_number") val phoneNumber: String,
)
