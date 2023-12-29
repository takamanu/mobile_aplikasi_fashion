package com.example.aifash.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.File


@Parcelize
data class FashionRequest(
    @SerializedName("fashion_name")
    val fashionName: String,
    @SerializedName("fashion_points")
    val fashionPoints: Int,
    @SerializedName("UserID")
    val userId: Int,
    @SerializedName("fashion_url_image")
    val file: File
) : Parcelable
