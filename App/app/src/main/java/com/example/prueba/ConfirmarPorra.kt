package com.example.prueba

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class ConfirmarPorra : AppCompatActivity() {
    var modoPorra: String = ""
    var opcionElegida: String = ""
    var idRonda = -1
    var temporada = -1
    var puntos = 0
    var idUser = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_porra)
        idRonda = intent.getIntExtra("IDRonda", -1)
        temporada = intent.getIntExtra("Temporada", -1)
        idUser = intent.getIntExtra("Usuario", -1)
        Log.i("IDUSUARIO", idUser.toString())

        var textoModo = findViewById<TextView>(R.id.modoPorra)
        textoModo.textSize = 18.0f
        var textoOpcion = findViewById<TextView>(R.id.opciones)
        textoOpcion.textSize = 15.0f
        modoPorra = intent.getStringExtra("Modo")!!

        if(modoPorra == "VueltaRapida"){
            textoModo.text = "El piloto con la vuelta rápida de carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "PitStop"){
            textoModo.text = "El equipo con la parada más rápida será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOPQualiEscuderia"){
            textoModo.text = "El equipo que se llevará la Pole Position será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOPCarreraEscuderia"){
            textoModo.text = "El equipo que ganará la carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOP3QualiPiloto"){
            val pilotos: ArrayList<String> = intent.getStringArrayListExtra("Opcion") as ArrayList<String>
            textoModo.text = "El top 3 de pilotos en clasificación serán: "
            textoOpcion.text = pilotos[0] + "\n" + pilotos[1] + "\n" + pilotos[2]
        }
        else if(modoPorra == "TOP3CarreraPiloto"){
            val pilotos: ArrayList<String> = intent.getStringArrayListExtra("Opcion") as ArrayList<String>
            textoModo.text = "El top 3 de pilotos en carrera serán: "
            textoOpcion.text = pilotos[0] + "\n" + pilotos[1] + "\n" + pilotos[2]
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun enviarPorra(view: View){
        val cliente = OkHttpClient()
        val future = CallbackFuture()
        val future2 = CallbackFuture()
        val futureDatosUser = CallbackFuture()
        val futureEditaPuntos = CallbackFuture()

        if(modoPorra == "VueltaRapida"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/fastest/1/results.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val nombrePiloto = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("Results")
                    .getJSONObject(0)
                    .getJSONObject("Driver")
                    .getString("givenName") + " " + json.getJSONObject("MRData")
                            .getJSONObject("RaceTable")
                            .getJSONArray("Races")
                            .getJSONObject(0)
                            .getJSONArray("Results")
                            .getJSONObject(0)
                            .getJSONObject("Driver")
                            .getString("familyName")

            Log.i("PETICION VUELTA RAPIDA", nombrePiloto)

            //Si existe acierto se realiza la operación de sumar puntos
            if(nombrePiloto == opcionElegida){
                //Primero obtenemos valor del acierto
                val requestPuntosPiloto = Request.Builder()
                        .url("http://192.168.1.14/?accion=obtenerpilotonombre&nombre=$opcionElegida")
                        .build()

                val response2 = cliente.newCall(requestPuntosPiloto).enqueue(future2)
                val json2 = JSONArray(future2.get()!!.body()!!.string())
                val puntos = json2.getJSONObject(0)
                        .getDouble("VALORVUELTARAPIDA")

                //Obtenemos los puntos del usuario
                val requestPuntosUsuario = Request.Builder()
                        .url("http://192.168.1.14/?accion=obtenerusuariobyid&iduser=$idUser")
                        .build()
                val responsePuntosUsuario = cliente.newCall(requestPuntosUsuario).enqueue(futureDatosUser)
                val jsonDatosUser = JSONArray(futureDatosUser.get()!!.body()!!.string())
                val puntosActuales = jsonDatosUser.getJSONObject(0)
                        .getDouble("Puntos")

                //Calculamos los nuevos puntos y modificamos
                val nuevosPuntos: Double = puntos + puntosActuales
                val requestModificaPuntos = Request.Builder()
                        .url("http://192.168.1.14/?accion=modificarpuntosusuario&iduser=$idUser&puntos=$nuevosPuntos")
                        .build()
                val responseModificaPuntos = cliente.newCall(requestModificaPuntos).enqueue(futureEditaPuntos)
            }
        }
        else if(modoPorra == "PitStop"){

        }
        else if(modoPorra == "TOPQualiEscuderia"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/qualifying/1.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val escuderia = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("QualifyingResults")
                    .getJSONObject(0)
                    .getJSONObject("Constructor")
                    .getString("name")

            Log.i("PETICION QUALIESC", escuderia)
            if(escuderia == opcionElegida){

            }
        }
        else if(modoPorra == "TOPCarreraEscuderia"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/results/1.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val escuderia = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("Results")
                    .getJSONObject(0)
                    .getJSONObject("Constructor")
                    .getString("name")

            Log.i("PETICION CARRERAESC", escuderia)
            if(escuderia == opcionElegida){
                
            }
        }
        else if(modoPorra == "TOP3QualiPiloto"){

        }
        else if(modoPorra == "TOP3CarreraPiloto"){

        }

        val volverPantallaLigas = Intent(this, PantallaLigas::class.java)
        volverPantallaLigas.putExtra("CambiaGP", true)

        startActivity(volverPantallaLigas)
    }
}