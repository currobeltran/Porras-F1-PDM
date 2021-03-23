package com.example.prueba

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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