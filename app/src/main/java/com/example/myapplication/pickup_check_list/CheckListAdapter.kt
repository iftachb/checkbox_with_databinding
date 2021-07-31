package com.example.myapplication.pickup_check_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.PickupCheckListItemBinding


class CheckListAdapter : DataBoundListAdapter<BaseViewModel>(object : DiffUtil.ItemCallback<BaseViewModel>() {
    override fun areItemsTheSame(oldItem: BaseViewModel, newItem: BaseViewModel) = oldItem == newItem
    override fun areContentsTheSame(oldItem: BaseViewModel, newItem: BaseViewModel) = oldItem.equals(newItem)
}) {

    override fun getItemViewType(position: Int): Int {
        return (position)
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.pickup_check_list_item, parent, false)
    }

    override fun bind(binding: ViewDataBinding, item: BaseViewModel) {
        when (binding) {
            is PickupCheckListItemBinding -> {
                binding.viewModel = item as CheckListViewModel
            }
        }
    }
}