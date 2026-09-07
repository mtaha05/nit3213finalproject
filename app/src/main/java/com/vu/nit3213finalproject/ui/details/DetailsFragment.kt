package com.vu.nit3213finalproject.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vu.nit3213finalproject.R
import com.vu.nit3213finalproject.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        val entityJson = arguments?.getString("entityJson")

        if (!entityJson.isNullOrBlank()) {
            displayEntityDetails(entityJson)
        }
    }

    private fun displayEntityDetails(entityJson: String) {

        val entity = JSONObject(entityJson)

        val detailsText = buildString {
            val keys = entity.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val value = entity.get(key)

                append("${formatKey(key)}\n")
                append("$value\n\n")
            }
        }

        binding.textEntityDetails.text = detailsText
    }

    private fun formatKey(key: String): String {
        return key
            .replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}