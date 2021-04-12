package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun botonRegistro(view: View){
        val intentregistro = Intent(this, Registro::class.java)

        startActivity(intentregistro)
    }

    fun botonInicioSesion(view: View){
        val intentinicioligas = Intent(this, PantallaLigas::class.java)

        startActivity(intentinicioligas)
    }
}