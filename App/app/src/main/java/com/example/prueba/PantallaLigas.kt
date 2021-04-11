package com.example.prueba

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject


class PantallaLigas : AppCompatActivity() {
    var idRonda: String = ""
    var temporada: String = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_ligas)
        val infocarrera = obtenerSiguienteRonda()
        val textocarrera = findViewById<TextView>(R.id.SiguienteCarrera)
        textocarrera.text = infocarrera
    }

    fun opcionesLiga(view: View){
        val intentopcionesliga = Intent(this, PantallaOpcionesLiga::class.java)
        intentopcionesliga.putExtra("IDLiga", 1)

        startActivity(intentopcionesliga)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun obtenerSiguienteRonda(): String{
        //Cada usuario tendría una liga asignada
        //Se obtendría por tanto la liga desde el usuario
        //En el ejemplo usaremos la liga con ID=1

        var infocarrera: String = ""
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()
        val futuretemporada = CallbackFuture()
        val futurenombrecarrera = CallbackFuture()

        //Peticion para obtener el ID de la ronda
        val peticion: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerronda&id=1")
                .build()

        val respuesta = cliente.newCall(peticion).enqueue(future)

        idRonda = future.get()!!.body()!!.string()
        Log.i("IDRONDA", idRonda)
        idRonda = idRonda.replace("\"","")
        ///////////////////////////////////////////////////////////////////////

        //Peticion para obtener la temporada
        val peticionTemporada: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenertemporada&id=1")
                .build()
        val respuestaTemporada = cliente.newCall(peticionTemporada).enqueue(futuretemporada)

        temporada = futuretemporada.get()!!.body()!!.string()
        Log.i("TEMPORADA", temporada)
        temporada = temporada.replace("\"","")
        ////////////////////////////////////////////////////////////////////////

        //Peticion para obtener la carrera desde la API
        val peticionCarreraApi: Request = Request.Builder()
                .url("http://ergast.com/api/f1/$temporada/$idRonda.json")
                .build()
        val respuestaCarreraApi = cliente.newCall(peticionCarreraApi).enqueue(futurenombrecarrera)

        val json: JSONObject = JSONObject(futurenombrecarrera.get()!!.body()!!.string())
        val nombrecarrera = json.getJSONObject("MRData")
                .getJSONObject("RaceTable")
                .getJSONArray("Races")
                .getJSONObject(0)
                .getString("raceName")
        val fechaCarrera = json.getJSONObject("MRData")
                .getJSONObject("RaceTable")
                .getJSONArray("Races")
                .getJSONObject(0)
                .getString("date")

        infocarrera = "$nombrecarrera: $fechaCarrera"
        Log.i("CARRERA", nombrecarrera)
        ////////////////////////////////////////////////////////////////////////

        return infocarrera
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun siguienteGranPremio(view: View){
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()
        val numeroRondaActual = idRonda.toInt()
        val siguienteRonda = numeroRondaActual + 1

        val peticion: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=cambiaronda&idliga=1&ronda=$siguienteRonda")
                .build()

        val respuesta = cliente.newCall(peticion).enqueue(future)

        this.recreate()
    }
}