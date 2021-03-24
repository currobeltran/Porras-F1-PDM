package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuPorras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_porras)
    }

    fun realizaPorra(view: View){
        val intentrealizaporra = Intent(this, RealizarPorra::class.java)

        startActivity(intentrealizaporra)
    }
}