package com.example.agroconnect.datamodel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserVoucherResponse(

	@field:SerializedName("data")
	val data: UserVoucherItem? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

