package com.example.prueba

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class Clasificacion : AppCompatActivity() {
    var idliga = -1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clasificacion)
        idliga = intent.getIntExtra("IDLiga",0)
        val json = obtenerClasificacionLiga()
        val layoutclasificacion = findViewById<LinearLayout>(R.id.layoutclasificacion)

        val parametros_layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        parametros_layout.setMargins(30,0,20,0)

        for (i in 0 until json.length()){
            val objeto = json.getJSONObject(i)

            var layoutusuario = LinearLayout(this)
            layoutusuario.orientation = LinearLayout.HORIZONTAL

            val textViewUsuario = TextView(this)
            val nombre = objeto.getString("Nombre")
            textViewUsuario.text = nombre
            textViewUsuario.textSize = 18.0f
            textViewUsuario.layoutParams = parametros_layout

            val textViewPuntos = TextView(this)
            val puntos = objeto.getString("Puntos")
            textViewPuntos.text = "$puntos puntos"
            textViewPuntos.textSize = 18.0f
            textViewPuntos.layoutParams = parametros_layout

            layoutusuario.addView(textViewUsuario)
            layoutusuario.addView(textViewPuntos)
            layoutclasificacion.addView(layoutusuario)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun obtenerClasificacionLiga(): JSONArray{
        val cliente = OkHttpClient()
        val future = CallbackFuture()

        val peticion = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerusuariosliga&idliga=$idliga")
                .build()

        val respuesta = cliente.newCall(peticion).enqueue(future)

        val salida = JSONArray(future.get()!!.body()!!.string())
        return salida
    }
}