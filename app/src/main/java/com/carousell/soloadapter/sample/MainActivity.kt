package com.carousell.soloadapter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.carousell.soloadapter.SoloAdapter
import com.carousell.soloadapter.sample.databinding.ActivityMainBinding
import com.carousell.soloadapter.sample.databinding.LayoutTextBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val dynamicAdapters = (1..100).map {
        dynamicBindAdapter("Click to hide $it")
    }
    private val fakeTitleAdapter =
        dynamicBindAdapter("Show/hide base on #3 of item", clickToHide = false).also {
            it.bindAdapter(dynamicAdapters[2])
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val viewBindingAdapter = viewBindingAdapter()
        val adapter = ConcatAdapter(
            staticAdapter(),
            customViewAdapter(),
            viewBindingAdapter
        )
        binding.recyclerView.adapter = adapter

        adapter.addAdapter(fakeTitleAdapter)
        dynamicAdapters.forEach {
            adapter.addAdapter(it)
        }

        binding.btnShowViewBindingAdapter.setOnClickListener {
            viewBindingAdapter.setShown(viewBindingAdapter.isShown().not())
        }
    }

    private fun staticAdapter() = SoloAdapter(R.layout.layout_text)

    private fun customViewAdapter(): SoloAdapter {
        val view = LayoutInflater.from(this).inflate(
            R.layout.layout_text,
            binding.recyclerView, false
        )
        view.findViewById<TextView>(R.id.textView).setText(R.string.show_all)
        view.setOnClickListener {
            dynamicAdapters.forEach {
                it.setShown(true)
            }
        }
        return SoloAdapter(view)
    }

    private fun dynamicBindAdapter(data: String, clickToHide: Boolean = true): SoloAdapter {
        val adapter = SoloAdapter(R.layout.layout_text)
        adapter.setViewOnBind { view ->
            view.findViewById<TextView>(R.id.textView).text = data
            if (clickToHide) {
                view.setOnClickListener {
                    adapter.setShown(false)
                }
            }
        }
        return adapter
    }

    private fun viewBindingAdapter(): SoloAdapter {
        val adapter = SoloAdapter(LayoutTextBinding.inflate(layoutInflater), shown = false)
        adapter.setViewBindingOnBind<LayoutTextBinding> {
            val randomInt = Random.nextInt()
            it.textView.text = getString(R.string.view_binding_text, randomInt)
            it.root.setOnClickListener {
                adapter.setShown(adapter.isShown().not())
            }
        }
        return adapter
    }
}