package com.vu.nit3213finalproject.ui.dashboard

sealed class DashboardUiState {

    data object Loading : DashboardUiState()

    data class Success(
        val entities: List<Map<String, Any?>>,
        val entityTotal: Int
    ) : DashboardUiState()

    data class Error(
        val message: String
    ) : DashboardUiState()
}