package pl.sjmprofil.grocerylist.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("user_name")
    val userName: String? = null,

    @SerializedName("user_password")
    val userPassword: String? = null
)