package com.example.prueba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

class ConfirmarPorra : AppCompatActivity() {
    var modoPorra: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_porra)

        var textoModo = findViewById<TextView>(R.id.modoPorra)
        var textoOpcion = findViewById<TextView>(R.id.opciones)
        modoPorra = intent.getStringExtra("Modo")!!

        if(modoPorra == "VueltaRapida"){
            textoModo.text = "Piloto con la vuelta rápida de carrera:"
            textoOpcion.text = intent.getStringExtra("Opcion")
        }
    }
}