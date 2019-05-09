package pl.sjmprofil.grocerylist.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "local_items")
data class LocalItem(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    var name: String? = null,

    var userName: String? = null,

    var userPassword: String? = null
)