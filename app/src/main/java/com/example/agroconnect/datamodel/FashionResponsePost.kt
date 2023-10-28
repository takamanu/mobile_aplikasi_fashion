package com.example.agroconnect.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FashionResponsePost(
    @SerializedName("data")
    val fashionItems: FashionItem,
    @SerializedName("message")
    val message: String
) : Parcelable




