package pl.sjmprofil.grocerylist.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("response")
    val responseValue: String? = null
)
