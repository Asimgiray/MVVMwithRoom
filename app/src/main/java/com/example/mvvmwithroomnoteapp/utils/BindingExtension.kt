package com.example.mvvmwithroomnoteapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun <DB : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes layoutRes: Int): Lazy<DB> {
    return lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView<DB>(this, layoutRes) }
}

fun <DB : ViewDataBinding> RecyclerView.Adapter<*>.dataBindingAdapter(@LayoutRes layoutRes: Int, inflater: LayoutInflater, parent: ViewGroup): Lazy<DB> {
    return lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.inflate(inflater, layoutRes, parent, false)}
}