package com.example.aifash.datamodel

import com.google.gson.annotations.SerializedName

data class DemandResponse(

    @field:SerializedName("data")
    val data: List<Demand>,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
