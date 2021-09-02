package uz.mobiler.pagination.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uz.mobiler.pagination.models.UserData

interface ApiService {

    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<UserData>

    @GET("users")
    suspend fun getUsers2(@Query("page") page: Int): UserData


}