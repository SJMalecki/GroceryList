package pl.sjmprofil.grocerylist.model

import android.arch.persistence.room.PrimaryKey

data class LocalItem(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val name: String? = null,

    val userName: String? = null,

    val userPassword: String? = null
)