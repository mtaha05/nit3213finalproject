package com.vu.nit3213finalproject.data.repository

import com.vu.nit3213finalproject.data.api.ApiService
import com.vu.nit3213finalproject.data.model.LoginRequest
import com.vu.nit3213finalproject.data.model.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(
        username: String,
        password: String
    ): LoginResponse {
        return apiService.login(
            LoginRequest(
                username = username,
                password = password
            )
        )
    }
}