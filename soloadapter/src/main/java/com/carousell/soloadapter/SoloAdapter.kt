package com.carousell.soloadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SoloAdapter : RecyclerView.Adapter<SoloAdapter.ViewHolder> {
    @LayoutRes
    private var layoutId: Int? = null
    private var view: View? = null

    private var bindFunction: ((itemView: View) -> Unit)? = null

    constructor(view: View) : super() {
        this.view = view
    }

    constructor(layoutId: Int) : super() {
        this.layoutId = layoutId
    }

    fun bind(bindFunction: (itemView: View) -> Unit) {
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
        bindFunction?.invoke(holder.itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}