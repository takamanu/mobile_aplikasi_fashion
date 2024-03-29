package com.example.aifash.auth.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val token: Token? = null
) : Parcelable

@Parcelize
data class Token(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null
) : Parcelable
