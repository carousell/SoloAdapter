package com.carousell.soloadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SoloAdapter<T> : RecyclerView.Adapter<SoloAdapter.ViewHolder> {
    @LayoutRes
    private var layoutId: Int? = null
    private var view: View? = null

    private var data: T? = null
    private var bindFunction: ((itemView: View, data: T) -> Unit)? = null

    constructor(view: View) : super() {
        this.view = view
    }

    constructor(layoutId: Int) : super() {
        this.layoutId = layoutId
    }

    fun setData(data: T, bindFunction: (itemView: View, data: T) -> Unit) {
        this.data = data
        this.bindFunction = bindFunction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holderView = view ?: layoutId?.let { layoutId ->
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        } ?: throw RuntimeException("No existing View or Layout Id to generate ViewHolder")
        return ViewHolder(holderView)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            bindFunction?.invoke(holder.itemView, it)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}