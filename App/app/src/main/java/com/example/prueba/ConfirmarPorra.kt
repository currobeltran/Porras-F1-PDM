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
        textoModo.textSize = 18.0f
        var textoOpcion = findViewById<TextView>(R.id.opciones)
        textoOpcion.textSize = 15.0f
        modoPorra = intent.getStringExtra("Modo")!!

        if(modoPorra == "VueltaRapida"){
            textoModo.text = "El piloto con la vuelta rápida de carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
        }
        else if(modoPorra == "PitStop"){
            textoModo.text = "El equipo con la parada más rápida será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
        }
        else if(modoPorra == "TOPQualiEscuderia"){
            textoModo.text = "El equipo que se llevará la Pole Position será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
        }
        else if(modoPorra == "TOPCarreraEscuderia"){
            textoModo.text = "El equipo que ganará la carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
        }
    }
}