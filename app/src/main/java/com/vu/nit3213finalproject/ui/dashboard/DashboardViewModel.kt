package com.vu.nit3213finalproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vu.nit3213finalproject.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _dashboardState =
        MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)

    val dashboardState: StateFlow<DashboardUiState> =
        _dashboardState.asStateFlow()

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardUiState.Loading

            try {
                val response =
                    dashboardRepository.getDashboard(keypass)

                _dashboardState.value =
                    DashboardUiState.Success(
                        entities = response.entities,
                        entityTotal = response.entityTotal
                    )

            } catch (exception: Exception) {
                _dashboardState.value =
                    DashboardUiState.Error(
                        "Unable to load dashboard data."
                    )
            }
        }
    }
}