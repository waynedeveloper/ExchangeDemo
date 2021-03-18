package com.waynelau.exchangedemo.ui.exchange

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waynelau.exchangedemo.App
import com.waynelau.exchangedemo.data.model.CurrencyInfo
import com.waynelau.exchangedemo.databinding.ItemCurrencyBinding

class CurrencyListAdapter(private val viewModel: CurrencyListViewModel): ListAdapter<CurrencyInfo, CurrencyListAdapter.ViewHolder>(CurrencyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(private val binding: ItemCurrencyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: CurrencyListViewModel, item: CurrencyInfo) {
            binding.vm = viewModel
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                Toast.makeText(App.instance, item.name, Toast.LENGTH_SHORT).show()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class CurrencyDiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}

@BindingAdapter("app:items")
fun setItems(rv: RecyclerView, items: List<CurrencyInfo>?) {
    items?.let {
        (rv.adapter as CurrencyListAdapter).submitList(it)
    }
}
