package com.vu.nit3213finalproject.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vu.nit3213finalproject.R
import com.vu.nit3213finalproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        setupLoginButton()
        observeLoginState()
    }

    private fun setupLoginButton() {
        binding.buttonLogin.setOnClickListener {

            val username =
                binding.editUsername.text?.toString()?.trim().orEmpty()

            val password =
                binding.editPassword.text?.toString()?.trim().orEmpty()

            viewModel.login(
                username = username,
                password = password
            )
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.loginState.collect { state ->

                    when (state) {

                        LoginUiState.Idle -> {
                            binding.loginProgressBar.isVisible = false
                            binding.textLoginError.isVisible = false
                        }

                        LoginUiState.Loading -> {
                            binding.loginProgressBar.isVisible = true
                            binding.textLoginError.isVisible = false
                            binding.buttonLogin.isEnabled = false
                        }

                        is LoginUiState.Success -> {
                            binding.loginProgressBar.isVisible = false
                            binding.buttonLogin.isEnabled = true

                            val args = Bundle().apply {
                                putString("keypass", state.keypass)
                            }

                            findNavController().navigate(
                                R.id.action_loginFragment_to_dashboardFragment,
                                args,
                                null
                            )
                        }

                        is LoginUiState.Error -> {
                            binding.loginProgressBar.isVisible = false
                            binding.buttonLogin.isEnabled = true
                            binding.textLoginError.isVisible = true
                            binding.textLoginError.text = state.message
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