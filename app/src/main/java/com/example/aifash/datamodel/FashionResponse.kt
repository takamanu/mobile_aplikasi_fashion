package com.example.aifash.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FashionResponse(
    @SerializedName("data")
    val fashionItems: List<FashionItem>,
    @SerializedName("message")
    val message: String
) : Parcelable




