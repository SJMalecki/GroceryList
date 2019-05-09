package pl.sjmprofil.grocerylist.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface LocalItemDao {

    @Query("SELECT * FROM local_items")
    fun selectAll(): List<LocalItem>

    @Insert
    fun insertLocalItem(localItem: LocalItem)

    @Delete
    fun deleteLocalItem(localItem: LocalItem)

    @Update
    fun updateLocalItem(localItem: LocalItem)

}