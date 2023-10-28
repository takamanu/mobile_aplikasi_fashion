package com.example.agroconnect.datamodel

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.example.agroconnect.auth.User
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserVoucherItem(

    @field:SerializedName("User")
	val user: User? = null,

    @field:SerializedName("Used")
	val used: Boolean? = null,

    @field:SerializedName("UserID")
	val userID: Int? = null,

    @field:SerializedName("CreatedAt")
	val createdAt: String? = null,

    @field:SerializedName("VoucherID")
	val voucherID: Int? = null,

    @field:SerializedName("ID")
	val iD: Int? = null,

    @field:SerializedName("DeletedAt")
	val deletedAt: String? = null,

    @field:SerializedName("UpdatedAt")
	val updatedAt: String? = null,

    @field:SerializedName("Voucher")
	val voucher: VoucherItem? = null
) : Parcelable


