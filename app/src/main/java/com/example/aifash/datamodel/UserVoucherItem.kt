package com.example.aifash.datamodel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserVoucherItem(

    @field:SerializedName("voucher_status")
	val used: String? = null,

    @field:SerializedName("user_id")
	val userID: Int? = null,

    @field:SerializedName("id")
    val ID: Int? = null,

    @field:SerializedName("voucher_id")
	val voucherID: Int? = null,

    @field:SerializedName("expiration_date")
    val voucherExpiredDate: String? = null


) : Parcelable


