package com.vu.nit3213finalproject.ui.login

sealed class LoginUiState {

    data object Idle : LoginUiState()

    data object Loading : LoginUiState()

    data class Success(
        val keypass: String
    ) : LoginUiState()

    data class Error(
        val message: String
    ) : LoginUiState()
}