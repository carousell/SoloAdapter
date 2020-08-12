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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ConcatAdapter(adapter1(), adapter2(), adapter3())
    }

    private fun adapter1() = SoloAdapter(R.layout.layout_text)

    private fun adapter2(): SoloAdapter {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_text, recyclerView, false)
        view.findViewById<TextView>(R.id.textView).setText(R.string.simple_text)
        return SoloAdapter(view)
    }

    private fun adapter3(): SoloAdapter {
        val data = "New string"
        val adapter = SoloAdapter(R.layout.layout_text)
        adapter.bind { view ->
            view.findViewById<TextView>(R.id.textView).text = data
        }
        return adapter
    }
}