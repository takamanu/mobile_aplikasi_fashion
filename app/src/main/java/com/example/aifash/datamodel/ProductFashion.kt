package com.example.aifash.datamodel

import com.example.aifash.auth.User
import com.google.gson.annotations.SerializedName

data class ProductFashion(

    @field:SerializedName("User")
    val user: User? = null,

    @field:SerializedName("category_id")
    val categoryId: Int? = null,

//    @field:SerializedName("user_id")
//    val userId: Int? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("points")
    val points: Int? = null,

    @field:SerializedName("name")
    var name: String? = null,
)
