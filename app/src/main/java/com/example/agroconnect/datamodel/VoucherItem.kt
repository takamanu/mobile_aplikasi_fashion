package com.example.agroconnect.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoucherItem(

	@field:SerializedName("voucher_name")
	val voucherName: String? = null,

	@field:SerializedName("voucher_price")
	val voucherPrice: Int? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("voucher_code")
	val voucherCode: String? = null,

	@field:SerializedName("voucher_expired_date")
	val voucherExpiredDate: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("DeletedAt")
	val deletedAt: String? = null,

	@field:SerializedName("voucher_url_image")
	val voucherUrlImage: String? = null,

	@field:SerializedName("UpdatedAt")
	val updatedAt: String? = null
) : Parcelable
