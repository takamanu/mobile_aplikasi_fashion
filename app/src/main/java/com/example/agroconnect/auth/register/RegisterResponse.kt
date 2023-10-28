package com.example.agroconnect.auth.register

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.agroconnect.auth.User
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable
