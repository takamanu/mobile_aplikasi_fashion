package com.example.aifash.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoucherItem(

	@field:SerializedName("voucher_name")
	val voucherName: String? = null,

	@field:SerializedName("voucher_price")
	val voucherPrice: Int? = null,

	@field:SerializedName("voucher_code")
	val voucherCode: String? = null,

	@field:SerializedName("id")
	val ID: Int? = null,

	@field:SerializedName("voucher_url_image")
	val voucherUrlImage: String? = null,

) : Parcelable
