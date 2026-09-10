package com.vu.nit3213finalproject.ui.login

import com.vu.nit3213finalproject.data.model.LoginResponse
import com.vu.nit3213finalproject.data.repository.LoginRepository
import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginRepository: LoginRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        loginRepository = mockk()

        Dispatchers.setMain(testDispatcher)

        mockkStatic(Log::class)

        every {
            Log.e(
                any(),
                any(),
                any<Throwable>()
            )
        } returns 0

        viewModel = LoginViewModel(loginRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `blank username or password returns error`() {

        viewModel.login("", "")

        val state = viewModel.loginState.value

        assertEquals(
            LoginUiState.Error(
                "Username and password are required."
            ),
            state
        )
    }

    @Test
    fun `successful login returns keypass`() = runTest(testDispatcher) {

        coEvery {
            loginRepository.login(
                username = "1234567",
                password = "TestName"
            )
        } returns LoginResponse(
            keypass = "technology"
        )

        viewModel.login(
            username = "1234567",
            password = "TestName"
        )

        advanceUntilIdle()

        assertEquals(
            LoginUiState.Success("technology"),
            viewModel.loginState.value
        )
    }

    @Test
    fun `failed login returns error state`() = runTest(testDispatcher) {

        coEvery {
            loginRepository.login(
                username = "1234567",
                password = "TestName"
            )
        } throws RuntimeException("Login failed")

        viewModel.login(
            username = "1234567",
            password = "TestName"
        )

        advanceUntilIdle()

        assertEquals(
            LoginUiState.Error(
                "Login failed. Please check your credentials."
            ),
            viewModel.loginState.value
        )
    }
}