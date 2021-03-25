package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MenuPorras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_porras)
    }

    fun realizaPorra(view: View){
        val intentrealizaporra = Intent(this, RealizarPorra::class.java)
        val idboton = view.id

        if(idboton == findViewById<Button>(R.id.top3quali).id){
            intentrealizaporra.putExtra("Titulo","Elija su top 3 para la clasificación")
        }
        else if(idboton == findViewById<Button>(R.id.top3carrera).id){
            intentrealizaporra.putExtra("Titulo","Elija su top 3 para la carrera")
        }
        else if(idboton == findViewById<Button>(R.id.pitstop).id){
            intentrealizaporra.putExtra("Titulo","Elija quien hará la parada más rápida")
        }
        else if(idboton == findViewById<Button>(R.id.vueltarapida).id){
            intentrealizaporra.putExtra("Titulo","Elija quien hará la vuelta rápida")
        }
        else if(idboton == findViewById<Button>(R.id.escuderiaquali).id){
            intentrealizaporra.putExtra("Titulo","Elija la escudería que conseguirá la pole")
        }
        else if(idboton == findViewById<Button>(R.id.escuderiacarrera).id){
            intentrealizaporra.putExtra("Titulo","Elija la escudería que conseguirá la victoria")
        }

        startActivity(intentrealizaporra)
    }
}