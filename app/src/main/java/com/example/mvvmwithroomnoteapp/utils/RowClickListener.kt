package com.example.mvvmwithroomnoteapp.utils

interface RowClickListener<T> {
    fun onRowClick(row: Int, item: T)
}