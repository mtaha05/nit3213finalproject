package com.vu.nit3213finalproject.data.api

import com.vu.nit3213finalproject.data.model.LoginRequest
import com.vu.nit3213finalproject.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("footscray/auth")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}