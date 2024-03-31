package com.example.aifash.auth

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserResponse(

	@field:SerializedName("status")
	val status: Boolean? = null,


	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val user: User? = null

) : Parcelable