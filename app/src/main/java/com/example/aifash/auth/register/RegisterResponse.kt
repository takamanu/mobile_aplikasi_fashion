package com.example.aifash.auth.register

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.aifash.auth.User
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: User? = null

) : Parcelable
