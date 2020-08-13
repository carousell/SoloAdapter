package com.carousell.soloadapter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.carousell.soloadapter.SoloAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dynamicAdapters = (1..100).map {
        dynamicBindAdapter(it + 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ConcatAdapter(
            staticAdapter(),
            customViewAdapter()
        )
        recyclerView.adapter = adapter

        dynamicAdapters.forEach {
            adapter.addAdapter(it)
        }
    }

    private fun staticAdapter() = SoloAdapter(R.layout.layout_text)

    private fun customViewAdapter(): SoloAdapter {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_text, recyclerView, false)
        view.findViewById<TextView>(R.id.textView).setText(R.string.show_all)
        view.setOnClickListener {
            dynamicAdapters.forEach {
                it.setShown(true)
            }
        }
        return SoloAdapter(view)
    }

    private fun dynamicBindAdapter(index: Int): SoloAdapter {
        val data = "Click to hide $index"
        val adapter = SoloAdapter(R.layout.layout_text)
        adapter.bind { view ->
            view.findViewById<TextView>(R.id.textView).text = data
            view.setOnClickListener {
                adapter.setShown(false)
            }
        }
        return adapter
    }
}