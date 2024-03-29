package com.example.aifash.auth

import android.os.Parcelable
import com.example.aifash.datamodel.FashionItem
import com.example.aifash.datamodel.UserVoucherItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("role")
    val role: String,

) : Parcelable
