package pl.sjmprofil.grocerylist.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("response")
    val response: List<Item>? = null
)
