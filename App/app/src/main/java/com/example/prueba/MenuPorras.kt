package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MenuPorras : AppCompatActivity() {
    var idRonda = -1
    var temporada = -1
    var idUser = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_porras)
        idRonda = intent.getIntExtra("IDRonda", -1)
        temporada = intent.getIntExtra("Temporada", -1)
        idUser = intent.getIntExtra("Usuario", -1)
    }

    fun realizaPorra(view: View){
        val intentrealizaporra = Intent(this, RealizarPorra::class.java)
        val idboton = view.id
        intentrealizaporra.putExtra("IDRonda", idRonda)
        intentrealizaporra.putExtra("Temporada", temporada)
        intentrealizaporra.putExtra("Usuario", idUser)

        if(idboton == findViewById<Button>(R.id.top3quali).id){
            intentrealizaporra.putExtra("Titulo","Elija su top 3 para la clasificación")
            intentrealizaporra.putExtra("Modo", "TOP3QualiPiloto")
        }
        else if(idboton == findViewById<Button>(R.id.top3carrera).id){
            intentrealizaporra.putExtra("Titulo","Elija su top 3 para la carrera")
            intentrealizaporra.putExtra("Modo", "TOP3CarreraPiloto")
        }
        else if(idboton == findViewById<Button>(R.id.vueltarapida).id){
            intentrealizaporra.putExtra("Titulo","Elija quien hará la vuelta rápida")
            intentrealizaporra.putExtra("Modo", "VueltaRapida")
        }
        else if(idboton == findViewById<Button>(R.id.escuderiaquali).id){
            intentrealizaporra.putExtra("Titulo","Elija la escudería que conseguirá la pole")
            intentrealizaporra.putExtra("Modo", "TOPQualiEscuderia")
        }
        else if(idboton == findViewById<Button>(R.id.escuderiacarrera).id){
            intentrealizaporra.putExtra("Titulo","Elija la escudería que conseguirá la victoria")
            intentrealizaporra.putExtra("Modo", "TOPCarreraEscuderia")
        }

        startActivity(intentrealizaporra)
    }
}