package com.vu.nit3213finalproject.ui.dashboard

import com.vu.nit3213finalproject.data.model.DashboardResponse
import com.vu.nit3213finalproject.data.repository.DashboardRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var dashboardRepository: DashboardRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dashboardRepository = mockk()

        Dispatchers.setMain(testDispatcher)

        viewModel = DashboardViewModel(dashboardRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `successful dashboard request returns entities`() =
        runTest(testDispatcher) {

            val mockEntities = listOf(
                mapOf(
                    "name" to "Little Black Dress",
                    "designer" to "Coco Chanel",
                    "yearIntroduced" to 1926.0,
                    "category" to "Dresses",
                    "material" to "Various",
                    "description" to "A classic fashion item"
                ),
                mapOf(
                    "name" to "Trench Coat",
                    "designer" to "Thomas Burberry",
                    "yearIntroduced" to 1912.0,
                    "category" to "Outerwear",
                    "material" to "Gabardine",
                    "description" to "A classic outerwear item"
                )
            )

            coEvery {
                dashboardRepository.getDashboard("fashion")
            } returns DashboardResponse(
                entities = mockEntities,
                entityTotal = 2
            )

            viewModel.loadDashboard("fashion")

            advanceUntilIdle()

            assertEquals(
                DashboardUiState.Success(
                    entities = mockEntities,
                    entityTotal = 2
                ),
                viewModel.dashboardState.value
            )

            coVerify(exactly = 1) {
                dashboardRepository.getDashboard("fashion")
            }
        }

    @Test
    fun `failed dashboard request returns error state`() =
        runTest(testDispatcher) {

            coEvery {
                dashboardRepository.getDashboard("fashion")
            } throws RuntimeException("Network error")

            viewModel.loadDashboard("fashion")

            advanceUntilIdle()

            assertEquals(
                DashboardUiState.Error(
                    "Unable to load dashboard data."
                ),
                viewModel.dashboardState.value
            )

            coVerify(exactly = 1) {
                dashboardRepository.getDashboard("fashion")
            }
        }
}