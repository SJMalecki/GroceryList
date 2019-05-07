package pl.sjmprofil.grocerylist.network

import android.content.Context
import pl.sjmprofil.grocerylist.model.Response
import pl.sjmprofil.grocerylist.model.User

class ApiRepository(private val context: Context, private val apiService: ApiService){

    suspend fun getRegisterData(): User {
        return apiService.performRegistration("seb1", "useb1", "pseb1").await()
    }

    suspend fun postRegisterData(s1: String, s2: String, s3: String): Response {
        return apiService.preformPostRegistration(s1, s2, s3).await()
    }
}