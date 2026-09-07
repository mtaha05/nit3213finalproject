package com.vu.nit3213finalproject.data.api

import com.vu.nit3213finalproject.data.model.LoginRequest
import com.vu.nit3213finalproject.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import com.vu.nit3213finalproject.data.model.DashboardResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @POST("footscray/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse

}
