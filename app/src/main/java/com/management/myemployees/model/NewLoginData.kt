package com.management.myemployees.model


import com.google.gson.annotations.SerializedName

data class NewLoginData(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("message")
    val message: Any? = Any(),
    @SerializedName("status")
    val status: String? = ""
) {
    data class Data(
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("token")
        val token: String? = ""
    )
}