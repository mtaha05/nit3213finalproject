package com.vu.nit3213finalproject.data.repository

import com.vu.nit3213finalproject.data.api.ApiService
import com.vu.nit3213finalproject.data.model.DashboardResponse
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDashboard(keypass: String): DashboardResponse {
        return apiService.getDashboard(keypass)
    }
}