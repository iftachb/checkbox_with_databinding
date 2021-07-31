package com.example.myapplication.pickup_check_list

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBoundListAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder>(
    AsyncDifferConfig.Builder<T>(diffCallback).build()
) {

    override fun onViewAttachedToWindow(holder: DataBoundViewHolder) {
        super.onViewAttachedToWindow(holder)
//        Log.d(TAG, "onViewAttachedToWindow $holder")
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: DataBoundViewHolder) {
        super.onViewDetachedFromWindow(holder)
//        Log.d(TAG, "onViewDetachedFromWindow $holder")
        holder.markDetach()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        val binding = createBinding(parent, viewType)
        val viewHolder = DataBoundViewHolder(binding)
        binding.lifecycleOwner = viewHolder

//        Log.d(TAG, "--> onCreateViewHolder $viewHolder $binding")
        return viewHolder
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        if (position < itemCount) {
//            Log.d(TAG, "onBindViewHolder $holder")
            bind(holder.binding, getItem(position))
            holder.binding.executePendingBindings()
        }
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding
    protected abstract fun bind(binding: ViewDataBinding, item: T)
}