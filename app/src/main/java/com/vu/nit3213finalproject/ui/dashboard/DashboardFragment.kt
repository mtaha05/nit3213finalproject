package com.vu.nit3213finalproject.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vu.nit3213finalproject.R
import com.vu.nit3213finalproject.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import org.json.JSONObject

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var entityAdapter: EntityAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDashboardBinding.bind(view)

        setupRecyclerView()
        observeDashboardState()

        val keypass = arguments?.getString("keypass")

        if (keypass.isNullOrBlank()) {
            binding.textDashboardError.text =
                getString(R.string.dashboard_error)
            binding.textDashboardError.isVisible = true
        } else {
            viewModel.loadDashboard(keypass)
        }
    }

    private fun setupRecyclerView() {

        entityAdapter = EntityAdapter { entity ->

            val entityJson = JSONObject(entity).toString()

            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundleOf(
                    "entityJson" to entityJson
                ),
                null
            )
        }

        binding.recyclerViewEntities.adapter = entityAdapter
    }

    private fun observeDashboardState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.dashboardState.collect { state ->
                    when (state) {
                        DashboardUiState.Loading -> {
                            binding.dashboardProgressBar.isVisible = true
                            binding.textDashboardError.isVisible = false
                        }

                        is DashboardUiState.Success -> {
                            binding.dashboardProgressBar.isVisible = false
                            binding.textDashboardError.isVisible = false
                            entityAdapter.updateData(state.entities)
                        }

                        is DashboardUiState.Error -> {
                            binding.dashboardProgressBar.isVisible = false
                            binding.textDashboardError.isVisible = true
                            binding.textDashboardError.text = state.message
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}