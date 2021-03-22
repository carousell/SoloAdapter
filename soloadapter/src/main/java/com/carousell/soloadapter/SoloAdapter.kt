package com.carousell.soloadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewbinding.ViewBinding

open class SoloAdapter : RecyclerView.Adapter<SoloAdapter.ViewHolder> {
    @LayoutRes
    private var layoutId: Int? = null
    private var view: View? = null
    private var shown: Boolean = true
    private var viewOnBind: ((itemView: View) -> Unit)? = null
    private var viewBinding: ViewBinding? = null
    private var viewBindingOnBind: ((viewBinding: ViewBinding) -> Unit)? = null

    private var bindAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>? = null
    private val bindAdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkBindAdapterVisibility()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            checkBindAdapterVisibility()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            checkBindAdapterVisibility()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkBindAdapterVisibility()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkBindAdapterVisibility()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            checkBindAdapterVisibility()
        }
    }

    constructor(view: View, shown: Boolean = true) : super() {
        this.view = view
        this.shown = shown
    }

    constructor(layoutId: Int, shown: Boolean = true) : super() {
        this.layoutId = layoutId
        this.shown = shown
    }

    constructor(
        viewBinding: ViewBinding,
        shown: Boolean = true
    ) : super() {
        this.viewBinding = viewBinding
        this.shown = shown
    }

    @Deprecated("Use setViewOnBind instead", ReplaceWith("setViewOnBind(bindFunction)"))
    fun bind(bindFunction: (itemView: View) -> Unit) {
        setViewOnBind(bindFunction)
    }

    fun setViewOnBind(viewOnBind: (itemView: View) -> Unit) {
        this.viewOnBind = viewOnBind
    }

    fun <T : ViewBinding> setViewBindingOnBind(bindFunction: (viewBinding: T) -> Unit) {
        this.viewBindingOnBind = bindFunction as (viewBinding: ViewBinding) -> Unit
    }

    fun checkBindAdapterVisibility() {
        bindAdapter?.let { target ->
            if (target.itemCount > 0 && !shown) {
                setShown(true)
            } else if (target.itemCount == 0 && shown) {
                setShown(false)
            }
        }
    }

    fun bindAdapter(target: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        bindAdapter = target
        target.registerAdapterDataObserver(bindAdapterDataObserver)
    }

    fun unBindAdapter(target: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        target.unregisterAdapterDataObserver(bindAdapterDataObserver)
        bindAdapter = null
    }

    fun setShown(isShown: Boolean) {
        if (shown != isShown) {
            shown = isShown
            if (shown) {
                notifyItemInserted(0)
            } else {
                notifyItemRemoved(0)
            }
        }
    }

    fun isShown() = shown

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holderView = when {
            view != null -> requireNotNull(view)
            layoutId != null -> LayoutInflater.from(parent.context)
                .inflate(requireNotNull(layoutId), parent, false)
            viewBinding != null -> requireNotNull(viewBinding).root
            else -> throw RuntimeException("No existing View or Layout Id to generate ViewHolder")
        }

        return ViewHolder(holderView)
    }

    override fun getItemCount() = if (shown) 1 else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            viewBinding != null -> viewBindingOnBind?.invoke(requireNotNull(viewBinding))
            else -> viewOnBind?.invoke(holder.itemView)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}