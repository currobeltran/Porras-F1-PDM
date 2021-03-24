package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PantallaOpcionesLiga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_opciones_liga)
    }

    fun verClasificacion(view: View){
        val intentclasificacion = Intent(this, Clasificacion::class.java)

        startActivity(intentclasificacion)
    }

    fun realizarPorra(view: View){
        val intentrealizaporra = Intent(this, MenuPorras::class.java)

        startActivity(intentrealizaporra)
    }
}