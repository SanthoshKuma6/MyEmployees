package com.management.myemployees.model


import com.google.gson.annotations.SerializedName

data class DepartmentListData(
    @SerializedName("data")
    val `data`: List<Data?>? = listOf()
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String? = "",
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("token")
        val token: String? = ""
    )
}