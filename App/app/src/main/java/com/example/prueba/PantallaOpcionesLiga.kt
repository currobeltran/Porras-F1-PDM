package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class PantallaOpcionesLiga : AppCompatActivity() {
    var idliga = -1
    var idRonda = -1
    var temporada = -1
    var idUser = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_opciones_liga)
        idliga = intent.getIntExtra("IDLiga",-1)
        idRonda = intent.getIntExtra("IDRonda", -1)
        temporada = intent.getIntExtra("Temporada", -1)
        idUser = intent.getIntExtra("Usuario", -1)
    }

    fun verClasificacion(view: View){
        val intentclasificacion = Intent(this, Clasificacion::class.java)
        intentclasificacion.putExtra("IDLiga",idliga)

        startActivity(intentclasificacion)
    }

    fun realizarPorra(view: View){
        val intentrealizaporra = Intent(this, MenuPorras::class.java)
        intentrealizaporra.putExtra("IDRonda", idRonda)
        intentrealizaporra.putExtra("Temporada", temporada)
        intentrealizaporra.putExtra("Usuario", idUser)

        startActivity(intentrealizaporra)
    }
}