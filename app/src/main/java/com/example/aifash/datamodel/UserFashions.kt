package com.example.aifash.datamodel

import android.os.Parcelable
import com.example.aifash.auth.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFashions(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("fashion_name")
    val fashionName: String? = null,

    @field:SerializedName("fashion_points")
    val fashionPoints: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("fashion_url_image")
    val fashionUrlImage: String? = null,


) : Parcelable


