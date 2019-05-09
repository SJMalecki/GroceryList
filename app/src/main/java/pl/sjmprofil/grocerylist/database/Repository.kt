package pl.sjmprofil.grocerylist.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(database: Database) {

    private val localItemDao = database.getLocalItemDao()

    suspend fun getAllLocalJokes(): MutableList<LocalItem> = withContext(Dispatchers.IO) {
        localItemDao.selectAll() as MutableList<LocalItem>
    }

    suspend fun addLocalJoke(localItem: LocalItem) = withContext(Dispatchers.IO) {
        localItemDao.insertLocalItem(localItem)
    }

    suspend fun deleteLocalJoke(localItem: LocalItem) = withContext(Dispatchers.IO) {
        localItemDao.deleteLocalItem(localItem)
    }
    
}