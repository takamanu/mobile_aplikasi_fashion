package com.example.aifash.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FashionItem(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("CreatedAt")
    val createdAt: String,
    @SerializedName("UpdatedAt")
    val updatedAt: String,
    @SerializedName("DeletedAt")
    val deletedAt: String?, // Nullable as indicated in the JSON
    @SerializedName("fashion_name")
    val fashionName: String,
    @SerializedName("fashion_points")
    val fashionPoints: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("fashion_url_image")
    val fashionUrlImage: String
) : Parcelable
