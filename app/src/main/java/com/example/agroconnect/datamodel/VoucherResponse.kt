package com.example.agroconnect.datamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoucherResponse(
    @SerializedName("data")
    val voucherItems: List<VoucherItem>,

    @SerializedName("message")
    val message: String
) : Parcelable
