package pl.sjmprofil.grocerylist.network

import kotlinx.coroutines.Deferred
import pl.sjmprofil.grocerylist.model.Response
import pl.sjmprofil.grocerylist.model.User
import retrofit2.http.*

interface ApiService {

    @GET("register.php")
    fun performRegistration(
        @Query("name") name: String,
        @Query("user_name") userName: String,
        @Query("user_password") userPassword: String
    ): Deferred<User>

    @FormUrlEncoded
    @POST("register.php")
    fun preformPostRegistration(
        @Field("name") name: String,
        @Field("userName") userName: String,
        @Field("userPassword") userPassword: String
    ): Deferred<Response>

    @FormUrlEncoded
    @POST("operations.php")
    fun performPostForDeleteItem(@Field("id") id: Int): Deferred<Response>
}