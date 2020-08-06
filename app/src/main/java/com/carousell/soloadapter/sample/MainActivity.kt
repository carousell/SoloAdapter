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
        recyclerView.adapter = SoloAdapter<Unit>(R.layout.layout_text)
    }

    private fun useCase2() {
        val text = TextView(this)
        text.setText(R.string.simple_text)
        recyclerView.adapter = SoloAdapter<Unit>(text)
    }

    private fun useCase3() {
        recyclerView.adapter = SoloAdapter<String>(R.layout.layout_text).also {
            it.setData("New string") { view, string ->
                view.findViewById<TextView>(R.id.textView).text = string
            }
        }

    }
}