package com.example.agroconnect.datamodel

import android.os.Parcelable
import com.example.agroconnect.auth.User
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
    val status: Boolean,
    @SerializedName("UserID")
    val userId: Int,
    @SerializedName("User")
    val user: User,
    @SerializedName("fashion_url_image")
    val fashionUrlImage: String
) : Parcelable
