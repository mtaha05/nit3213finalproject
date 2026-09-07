package com.vu.nit3213finalproject.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vu.nit3213finalproject.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    val loginState: StateFlow<LoginUiState> =
        _loginState.asStateFlow()

    fun login(username: String, password: String) {

        if (username.isBlank() || password.isBlank()) {
            _loginState.value =
                LoginUiState.Error("Username and password are required.")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading

            try {
                val response = loginRepository.login(
                    username = username,
                    password = password
                )

                _loginState.value =
                    LoginUiState.Success(response.keypass)

            } catch (exception: Exception) {
                _loginState.value =
                    LoginUiState.Error(
                        "Login failed. Please check your credentials."
                    )
            }
        }
    }
}