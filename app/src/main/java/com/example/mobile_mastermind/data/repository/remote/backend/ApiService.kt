package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.response.SuccessWrapper
import com.example.mobile_mastermind.data.repository.remote.response.game.GetCategoriesResponse
import com.example.mobile_mastermind.data.repository.remote.response.users.PostLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    //Login
    @POST("api/users/login")
    suspend fun postLoginUser(
        @Body loginUserRequest: LoginUserRequest
    ): Response<SuccessWrapper<PostLoginResponse>>

    //Register
    @Multipart
    @POST("api/users/register")
    suspend fun postRegister(
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<SuccessWrapper<PostLoginResponse>>

    //Logout
    @POST("api/users/logout")
    suspend fun postLogout(
    ): Response<SuccessWrapper<Boolean>>

    //Get all categories
    @GET("api/category")
    suspend fun getCategories(
    ): Response<SuccessWrapper<GetCategoriesResponse>>
}