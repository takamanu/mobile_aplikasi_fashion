package com.example.aifash.datamodel

import com.google.gson.annotations.SerializedName

data class FormatResponse<T>(

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: T? = null
)
