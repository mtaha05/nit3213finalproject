package com.vu.nit3213finalproject.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vu.nit3213finalproject.databinding.ItemEntityBinding

class EntityAdapter(
    private val onEntityClick: (Map<String, Any?>) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    private var entities: List<Map<String, Any?>> = emptyList()

    fun updateData(newEntities: List<Map<String, Any?>>) {
        entities = newEntities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntityViewHolder {

        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EntityViewHolder,
        position: Int
    ) {
        holder.bind(entities[position])
    }

    override fun getItemCount(): Int = entities.size

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Map<String, Any?>) {

            val summaryProperties = entity.filterKeys {
                !it.equals("description", ignoreCase = true)
            }

            val firstProperty = summaryProperties.entries.firstOrNull()

            binding.textEntityTitle.text =
                firstProperty?.value?.toString() ?: "Entity"

            binding.textEntitySummary.text =
                summaryProperties.entries
                    .drop(1)
                    .joinToString("\n") { entry ->
                        "${formatKey(entry.key)}: ${formatValue(entry.value)}"
                    }

            binding.root.setOnClickListener {
                onEntityClick(entity)
            }
        }

        private fun formatKey(key: String): String {
            return key
                .replace("_", " ")
                .replace(Regex("([a-z])([A-Z])"), "$1 $2")
                .replaceFirstChar { it.uppercase() }
        }

        private fun formatValue(value: Any?): String {
            return when (value) {
                is Double -> {
                    if (value % 1.0 == 0.0) {
                        value.toInt().toString()
                    } else {
                        value.toString()
                    }
                }

                else -> value?.toString() ?: ""
            }
        }
    }
}