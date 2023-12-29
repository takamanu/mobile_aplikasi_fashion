package com.example.aifash.auth

import android.os.Parcelable
import com.example.aifash.datamodel.FashionItem
import com.example.aifash.datamodel.UserVoucherItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("UpdatedAt")
    val updatedAt: String,
    @SerializedName("DeletedAt")
    val deletedAt: String?, // Nullable as indicated in the JSON
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: Int,
    @SerializedName("fashion")
    val fashion: List<FashionItem?>?, // The type is not specified in the JSON
    @SerializedName("user_voucher")
    val userVouchers: List<UserVoucherItem?>? // The type is not specified in the JSON
) : Parcelable
