package com.example.prueba

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.marginRight
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.w3c.dom.Text

class RealizarPorra : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_porra)

        val titulo = findViewById<TextView>(R.id.textView14)
        titulo.text = intent.getStringExtra("Titulo")
        var lay: LinearLayout = findViewById(R.id.layout_pilotos)
        val modo = intent.getStringExtra("Modo")

        if (modo == "TOP3QualiPiloto" || modo == "TOP3CarreraPiloto" || modo == "VueltaRapida"){
            val jsonPilotos = ObtenerPilotos()

            for(i in 0 until jsonPilotos.length()){
                val piloto = jsonPilotos.getJSONObject(i)

                var lay2: LinearLayout = LinearLayout(this)
                lay2.orientation = LinearLayout.HORIZONTAL
                lay.addView(lay2)

                val parametros_layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                parametros_layout.setMargins(20,0,20,0)

                var nombrePiloto: TextView = TextView(this)
                nombrePiloto.text = piloto.getString("NOMBRE")
                nombrePiloto.textSize = 18.0f
                nombrePiloto.layoutParams = parametros_layout

                var valorPiloto: TextView = TextView(this)
                if(modo == "TOP3QualiPiloto"){
                    valorPiloto.text = piloto.getString("VALORCLASIFICACION")
                }
                else if(modo == "TOP3CarreraPiloto"){
                    valorPiloto.text = piloto.getString("VALORCARRERA")
                }
                else{
                    valorPiloto.text = piloto.getString("VALORVUELTARAPIDA")
                }
                valorPiloto.textSize = 18.0f
                valorPiloto.layoutParams = parametros_layout

                var check = CheckBox(this)

                lay2.addView(nombrePiloto)
                lay2.addView(valorPiloto)
                lay2.addView(check)
            }
        }
        else{
            val jsonEscuderias = ObtenerEscuderias()

            for(i in 0 until jsonEscuderias.length()){
                val escuderia = jsonEscuderias.getJSONObject(i)

                var lay2: LinearLayout = LinearLayout(this)
                lay2.orientation = LinearLayout.HORIZONTAL
                lay.addView(lay2)

                val parametros_layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                parametros_layout.setMargins(20,0,20,0)

                var nombreescuderia: TextView = TextView(this)
                nombreescuderia.text = escuderia.getString("NOMBRE")
                nombreescuderia.textSize = 18.0f
                nombreescuderia.layoutParams = parametros_layout
                
                //TODO: Colocar la información de la BBDD en los TextView correspondientes
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ObtenerPilotos(): JSONArray{
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()
        val future2 = CallbackFuture()

        //Se divide en 2 peticiones porque en 1 se sobrecarga el buffer de lectura
        val peticion: Request = Request.Builder()
                .url("http://192.168.1.17/?accion=obtenerpilotos&from=1&to=10")
                .build()
        val respuesta = cliente.newCall(peticion).enqueue(future)
        var primeraparte: JSONArray = JSONArray(future.get()!!.body()!!.string())

        val peticion2: Request = Request.Builder()
                .url("http://192.168.1.17/?accion=obtenerpilotos&from=11&to=20")
                .build()
        val respuesta2 = cliente.newCall(peticion2).enqueue(future2)
        val segundaparte: JSONArray = JSONArray(future2.get()!!.body()!!.string())

        for(i in 0 until segundaparte.length()){
            primeraparte.put(segundaparte.getJSONObject(i))
        }

        return primeraparte
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ObtenerEscuderias(): JSONArray{
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()

        val peticion: Request = Request.Builder()
                .url("http://192.168.1.17/?accion=obtenerescuderias")
                .build()
        val respuesta = cliente.newCall(peticion).enqueue(future)

        return JSONArray(future.get()!!.body()!!.string())
    }
}