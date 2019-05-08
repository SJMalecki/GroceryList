package pl.sjmprofil.grocerylist.model

import com.google.gson.annotations.SerializedName

data class JsonObject(
    @SerializedName("response")
    val response: List<Item>? = null
)
