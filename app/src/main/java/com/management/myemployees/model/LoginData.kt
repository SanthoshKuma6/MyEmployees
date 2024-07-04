package com.management.myemployees.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginData(
        @SerializedName("message")
        @Expose val message: String? = "",
        @SerializedName("name")
        @Expose val name: String? = "",
        @SerializedName("token")
        @Expose val token: String? = ""
    )
