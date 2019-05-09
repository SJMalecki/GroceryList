package pl.sjmprofil.grocerylist.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [LocalItem::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun getLocalItemDao(): LocalItemDao
}