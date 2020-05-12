package com.example.viewpagerkotlin.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    protected abstract fun clear()
}