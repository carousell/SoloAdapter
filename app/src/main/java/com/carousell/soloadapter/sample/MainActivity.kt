package com.carousell.soloadapter.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.carousell.soloadapter.SoloAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        useCase1()
    }

    private fun useCase1() {
        recyclerView.adapter = SoloAdapter(R.layout.layout_text)
    }

    private fun useCase2() {
        val text = TextView(this)
        text.setText(R.string.simple_text)
        recyclerView.adapter = SoloAdapter(text)
    }

    private fun useCase3() {
        val data = "New string"
        val adapter = SoloAdapter(R.layout.layout_text)
        adapter.bind { view ->
            view.findViewById<TextView>(R.id.textView).text = data
        }
        recyclerView.adapter = adapter
    }
}