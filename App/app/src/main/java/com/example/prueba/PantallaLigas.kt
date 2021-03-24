package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class PantallaLigas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_ligas)
    }

    fun opcionesLiga(view: View){
        val intentopcionesliga = Intent(this, PantallaOpcionesLiga::class.java)

        startActivity(intentopcionesliga)
    }
}