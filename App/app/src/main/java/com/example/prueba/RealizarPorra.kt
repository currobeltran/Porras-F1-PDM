package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text

class RealizarPorra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_porra)

        val titulo = findViewById<TextView>(R.id.textView14)
        titulo.text = intent.getStringExtra("Titulo")
    }
}