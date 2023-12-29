package com.example.aifash.datamodel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserVoucherItem(

    @field:SerializedName("ID")
    val iD: Int? = null,

    @field:SerializedName("status")
	val used: Boolean? = null,

    @field:SerializedName("user_id")
	val userID: Int? = null,

    @field:SerializedName("CreatedAt")
	val createdAt: String? = null,

    @field:SerializedName("VoucherID")
	val voucherID: Int? = null,

    @field:SerializedName("Voucher")
    val voucher: VoucherItem? = null,

    @field:SerializedName("DeletedAt")
	val deletedAt: String? = null,

    @field:SerializedName("UpdatedAt")
	val updatedAt: String? = null,

    @field:SerializedName("voucher_expired_date")
    val voucherExpiredDate: String? = null


) : Parcelable


